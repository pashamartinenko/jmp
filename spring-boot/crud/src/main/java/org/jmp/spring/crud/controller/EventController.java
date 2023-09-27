package org.jmp.spring.crud.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.crud.facade.BookingFacade;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/events")
public class EventController
{
    private final BookingFacade bookingFacade;

    @GetMapping("/{eventId}")
    public EventImpl getEventById(@PathVariable Long eventId) {
        log.info("GET events by id={}", eventId);
        return bookingFacade.getEventById(eventId);
    }

    @RequestMapping(method=RequestMethod.GET, params = "title")
    public List<EventImpl> getEventsByTitle(@RequestParam String title, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by title={}, pageSize={}, pageNum={}", title, pageSize, pageNum);
        return bookingFacade.getEventsByTitle(title, pageSize, pageNum);
    }

    @RequestMapping(method=RequestMethod.GET, params = "day")
    public List<EventImpl> getEventsForDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date day, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by day={}, pageSize={}, pageNum={}", day, pageSize, pageNum);
        return bookingFacade.getEventsForDay(day, pageSize, pageNum);
    }

    @PostMapping
    public ResponseEntity<EventImpl> createEvent(@RequestBody EventImpl event)
    {
        log.info("POST event {}", event);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingFacade.createEvent(event));

    }

    @PutMapping(value = "/{eventId}")
    public EventImpl updateEvent(@PathVariable Long eventId, @RequestBody EventImpl event)
    {
        log.info("PATCH /events/id/{}, event={}", eventId, event);
        event.setId(eventId);
        return bookingFacade.updateEvent(event);
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        log.info("DELETE /events/id/{}", eventId);
        bookingFacade.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
