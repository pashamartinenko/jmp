package org.jmp.spring.crud.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.crud.facade.BookingFacade;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/events")
public class EventController
{
    private final BookingFacade bookingFacade;

    @GetMapping("/id/{eventId}")
    public EventImpl getEventById(@PathVariable Long eventId) {
        log.info("GET events by id={}", eventId);
        return bookingFacade.getEventById(eventId);
    }

    @GetMapping("/title/{title}")
    public List<EventImpl> getEventsByTitle(@PathVariable String title, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by title={}, pageSize={}, pageNum={}", title, pageSize, pageNum);
        return bookingFacade.getEventsByTitle(title, pageSize, pageNum);
    }

    @GetMapping("/day/{day}")
    public List<EventImpl> getEventsForDay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date day, @RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(name = "offset", defaultValue = "1") Integer pageNum) {
        log.info("GET events by day={}, pageSize={}, pageNum={}", day, pageSize, pageNum);
        return bookingFacade.getEventsForDay(day, pageSize, pageNum);
    }

    @PostMapping(value = "/")
    public EventImpl createEvent(@RequestBody EventImpl event)
    {
        log.info("POST event {}", event);
        return bookingFacade.createEvent(event);
    }

    @PatchMapping(value = "/id/{eventId}")
    public EventImpl updateEvent(@PathVariable Long eventId, @RequestBody EventImpl event)
    {
        log.info("PATCH /events/id/{}, event={}", eventId, event);
        event.setId(eventId);
        return bookingFacade.updateEvent(event);
    }

    @DeleteMapping(value = "/id/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        log.info("DELETE /events/id/{}", eventId);
        bookingFacade.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
