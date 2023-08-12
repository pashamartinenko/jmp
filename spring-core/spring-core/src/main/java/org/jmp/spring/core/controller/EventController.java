package org.jmp.spring.core.controller;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.impl.EventImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class EventController
{
    private static final String EVENTS_VIEW = "events";
    private final BookingFacade bookingFacade;

    @GetMapping("/events/id/{eventId}")
    public ModelAndView getEventById(@PathVariable Long eventId) {
        log.info(format("GET events by id=%d", eventId));
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW);
        EventImpl event = bookingFacade.getEventById(eventId);
        modelAndView.addObject("events", List.of(event));
        return modelAndView;
    }

    @GetMapping("/events/title/{title}")
    public ModelAndView getEventsByTitle(@PathVariable String title, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info(format("GET events by title=%s, pageSize=%d, pageNum=%d", title, pageSize, pageNum));
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW);
        List<EventImpl> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        modelAndView.addObject("events", events);
        return modelAndView;
    }

    @GetMapping("/events/day/{day}")
    public ModelAndView getEventsForDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date day, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info(format("GET events by day=%s, pageSize=%d, pageNum=%d", day, pageSize, pageNum));
        ModelAndView modelAndView = new ModelAndView(EVENTS_VIEW);
        List<EventImpl> events = bookingFacade.getEventsForDay(day, pageSize, pageNum);
        modelAndView.addObject("events", events);
        return modelAndView;
    }
}
