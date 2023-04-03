package org.jmp.spring.core.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.EventDao;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Slf4j
@Setter
public class EventServiceImpl implements EventService
{
    private static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private EventDao eventDao;

    public Event getEventById(long eventId) {
        log.info("get events by id {}", eventId);
        return eventDao.getEventById(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("get events by title {}", title);
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("get events for day {}", day);
        return eventDao.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        log.info("create event {}", event);
        return eventDao.createEvent(event);
    }

    public Event updateEvent(Event event) {
        log.info("update event {}", event);
        return eventDao.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        log.info("remove event by id {}", eventId);
        return eventDao.deleteEvent(eventId);
    }
}
