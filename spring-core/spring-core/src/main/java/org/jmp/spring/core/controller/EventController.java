package org.jmp.spring.core.controller;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.impl.EventImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EventController
{
    private final BookingFacade bookingFacade;

    @GetMapping("/events/{eventId}")
    public ModelAndView getEventById(@PathVariable Long eventId) {
        log.info(format("GET /events/%d", eventId));
        ModelAndView modelAndView = new ModelAndView("event");
        EventImpl event = bookingFacade.getEventById(eventId);
        modelAndView.addObject("event", event);
        return modelAndView;
    }
}
