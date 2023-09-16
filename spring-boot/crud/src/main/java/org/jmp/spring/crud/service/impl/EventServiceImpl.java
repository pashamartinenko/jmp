package org.jmp.spring.crud.service.impl;

import static java.lang.String.format;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.crud.dao.EventDao;
import org.jmp.spring.crud.model.Event;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.jmp.spring.crud.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Setter
public class EventServiceImpl implements EventService

{
    @Autowired
    private EventDao eventDao;

    public EventImpl getEventById(long eventId) {
        log.info("get events by id {}", eventId);
        return eventDao.findById(eventId)
                .orElseThrow(() -> new RuntimeException(format("Event with id=%d does not exist", eventId)));
    }

    public List<EventImpl> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("get events by title {}", title);
        return eventDao.findAllByTitleContainingIgnoreCase(title, PageRequest.of(pageNum - 1, pageSize));
    }

    public List<EventImpl> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("get events for day {}", day);
        return eventDao.findAllByDate(day, PageRequest.of(pageNum - 1, pageSize));
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
