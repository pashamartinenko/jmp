package org.jmp.spring.crud.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.jmp.spring.crud.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/events")
public class EventController
{
    
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public EventImpl getEventById(@PathVariable Long eventId) {
        log.info("GET events by id={}", eventId);
        return eventService.getEventById(eventId);
    }

    @PostMapping
    public ResponseEntity<EventImpl> createEvent(@RequestBody EventImpl event)
    {
        log.info("POST event {}", event);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(event));

    }

    @PutMapping(value = "/{eventId}")
    public EventImpl updateEvent(@PathVariable Long eventId, @RequestBody EventImpl event)
    {
        log.info("PATCH /events/id/{}, event={}", eventId, event);
        event.setId(eventId);
        return eventService.updateEvent(event);
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        log.info("DELETE /events/id/{}", eventId);
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
