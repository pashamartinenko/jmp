package org.jmp.spring.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.impl.EventImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/events")
public class EventController
{
    private static final String EVENTS_VIEW_NAME = "events";
    private static final String EVENTS_MODEL_NAME = "events";
    private final BookingFacade bookingFacade;

    @GetMapping("/id/{eventId}")
    public ModelAndView getEventById(@PathVariable Long eventId) {
        log.info("GET events by id={}", eventId);
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW_NAME);
        EventImpl event = bookingFacade.getEventById(eventId);
        modelAndView.addObject(EVENTS_MODEL_NAME, List.of(event));
        return modelAndView;
    }

    @GetMapping("/title/{title}")
    public ModelAndView getEventsByTitle(@PathVariable String title, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by title={}, pageSize={}, pageNum={}", title, pageSize, pageNum);
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW_NAME);
        List<EventImpl> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        modelAndView.addObject(EVENTS_MODEL_NAME, events);
        return modelAndView;
    }

    @GetMapping("/day/{day}")
    public ModelAndView getEventsForDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date day, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by day={}, pageSize={}, pageNum={}", day, pageSize, pageNum);
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW_NAME);
        List<EventImpl> events = bookingFacade.getEventsForDay(day, pageSize, pageNum);
        modelAndView.addObject(EVENTS_MODEL_NAME, events);
        return modelAndView;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createEvent(@RequestParam Map<String, String> parameters) throws ParseException
    {
        log.info("POST /events parameters: {}", parameters);
        String title = parameters.get("title");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = parameters.get("date");
        Date date = dateString != null ? formatter.parse(dateString) : null;
        String priceString = parameters.get("price");
        Long price = priceString != null ? Long.valueOf(priceString) : null;
        EventImpl event = new EventImpl(title, date, price);

        EventImpl createdEvent = bookingFacade.createEvent(event);

        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW_NAME);
        modelAndView.addObject(EVENTS_MODEL_NAME, List.of(createdEvent));
        return modelAndView;
    }

    @PatchMapping(value = "/id/{eventId}")
    public ModelAndView updateEvent(@PathVariable Long eventId, @RequestBody MultiValueMap<String, String> parameters) throws ParseException
    {
        log.info("PATCH /events/id/{} parameters: {}", eventId, parameters);
        String title = parameters.getFirst("title");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = parameters.getFirst("date");
        Date date = dateString != null ? formatter.parse(dateString) : null;
        String priceString = parameters.getFirst("price");
        Long price = priceString != null ? Long.valueOf(priceString) : null;
        EventImpl event = new EventImpl(eventId, title, date, price);

        EventImpl createdEvent = bookingFacade.updateEvent(event);

        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW_NAME);
        modelAndView.addObject(EVENTS_MODEL_NAME, List.of(createdEvent));
        return modelAndView;
    }

    @DeleteMapping(value = "/id/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        log.info("DELETE /events/id/{}", eventId);
        bookingFacade.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
