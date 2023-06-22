package org.jmp.spring.core.controller;

import lombok.RequiredArgsConstructor;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.impl.EventImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/events")
public class EventController
{
    private final BookingFacade bookingFacade;


    @GetMapping("/{eventId}")
    public EventImpl getEventById(@PathVariable Long eventId) {
        return bookingFacade.getEventById(eventId);
    }
}
