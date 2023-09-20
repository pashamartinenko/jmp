package org.jmp.spring.mvc.controller;

import static java.lang.String.format;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.mvc.facade.BookingFacade;
import org.jmp.spring.mvc.model.Ticket;
import org.jmp.spring.mvc.model.impl.EventImpl;
import org.jmp.spring.mvc.model.impl.TicketImpl;
import org.jmp.spring.mvc.model.impl.UserImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController
{

    private static final String TICKETS_VIEW_NAME = "tickets";
    private static final String TICKETS_MODEL_NAME = "tickets";

    private final BookingFacade bookingFacade;

    private final TemplateEngine templateEngine;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView bookTicket(@RequestParam Map<String, String> parameters)
    {
        log.info("Create ticket using parameters: {}", parameters);
        String userIdString = parameters.get("userId");
        Long userId = userIdString != null ? Long.valueOf(userIdString) : null;
        String eventIdString = parameters.get("eventId");
        Long eventId = eventIdString != null ? Long.valueOf(eventIdString) : null;
        String placeString = parameters.get("place");
        Integer place = placeString != null ? Integer.valueOf(placeString) : null;
        String categoryString = parameters.get("category");
        Ticket.Category category = categoryString != null ? Ticket.Category.valueOf(categoryString) : null;

        TicketImpl createdTicket = bookingFacade.bookTicket(userId, eventId, place, category);

        ModelAndView modelAndView = new ModelAndView(TICKETS_VIEW_NAME);
        modelAndView.addObject(TICKETS_MODEL_NAME, createdTicket);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "userId", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getBookedTicketsPdf(@RequestParam Long userId, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) throws DocumentException
    {
        log.info("Get booked tickets in pdf for user with id={}", userId);
        UserImpl user = bookingFacade.getUserById(userId);
        List<TicketImpl> bookedTickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable(TICKETS_MODEL_NAME, bookedTickets);
        String html = templateEngine.process(TICKETS_VIEW_NAME, thymeleafContext);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", format("ticket_for_user_%d.pdf", userId));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(outputStream.toByteArray());
    }

    @RequestMapping(method = RequestMethod.GET, params = "userId")
    public ModelAndView getBookedTicketsHtml(@RequestParam Long userId, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum)
    {
        log.info("Get booked tickets in html for user with id={}", userId);
        UserImpl user = bookingFacade.getUserById(userId);
        List<TicketImpl> bookedTickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        ModelAndView modelAndView = new ModelAndView(TICKETS_VIEW_NAME);
        modelAndView.addObject(TICKETS_MODEL_NAME, bookedTickets);
        return modelAndView;
    }

    @DeleteMapping(value = "/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId)
    {
        log.info("DELETE /tickets/{}", ticketId);
        bookingFacade.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, params = "eventId")
    public ModelAndView getBookedTickets(@RequestParam Long eventId, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum)
    {
        log.info("Get booked tickets for event with id={}", eventId);
        EventImpl event = bookingFacade.getEventById(eventId);
        List<TicketImpl> bookedTickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
        ModelAndView modelAndView = new ModelAndView(TICKETS_VIEW_NAME);
        modelAndView.addObject(TICKETS_MODEL_NAME, bookedTickets);
        return modelAndView;
    }

    @GetMapping(value = "/preload")
    public ResponseEntity<String> preload()
    {
        log.info("GET /preload");
        bookingFacade.preloadTickets();
        return ResponseEntity.ok().build();
    }
}
