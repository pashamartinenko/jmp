package org.jmp.spring.core.controller;

import lombok.RequiredArgsConstructor;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController
{

    private static final String TICKETS_VIEW_NAME = "tickets";
    private static final String TICKETS_MODEL_NAME = "tickets";

    private final BookingFacade bookingFacade;


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView bookTicket(@RequestParam Map<String, String> parameters) {
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
}
