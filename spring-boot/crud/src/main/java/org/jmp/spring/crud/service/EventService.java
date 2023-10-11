package org.jmp.spring.crud.service;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.crud.dao.EventDao;
import org.jmp.spring.crud.model.Event;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService
{

    private final EventDao eventDao;

    public EventImpl getEventById(long eventId) {
        log.info("get events by id {}", eventId);
        return eventDao.findById(eventId)
                .orElseThrow(() -> new RuntimeException(format("Event with id=%d does not exist", eventId)));
    }

    public EventImpl createEvent(EventImpl event) {
        log.info("create event {}", event);
        return eventDao.save(event);
    }

    public EventImpl updateEvent(EventImpl event) {
        Event existingEvent = getEventById(event.getId());
        log.info("update event {}", existingEvent);
        return eventDao.save(event);
    }

    public boolean deleteEvent(long eventId) {
        log.info("remove event by id {}", eventId);
        boolean isFound = eventDao.existsById(eventId);
        if(isFound) {
            eventDao.deleteById(eventId);
        }
        return isFound;
    }
}
