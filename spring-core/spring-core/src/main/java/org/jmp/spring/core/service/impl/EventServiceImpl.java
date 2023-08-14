package org.jmp.spring.core.service.impl;

import static java.lang.String.format;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.EventDao;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.service.EventService;
import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Setter
public class EventServiceImpl implements EventService
{
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
        EventImpl foundEvent = getEventById(event.getId());

        String title = event.getTitle();
        if(title != null) {
            foundEvent.setTitle(title);
        }
        Long price = event.getPrice();
        if(price != null) {
            foundEvent.setPrice(price);
        }
        Date date = event.getDate();
        if(date != null) {
            foundEvent.setDate(date);
        }
        log.info("update event {}", foundEvent);
        return eventDao.save(foundEvent);
    }

    public boolean deleteEvent(long eventId) {
        log.info("remove event by id {}", eventId);
        boolean isFound = eventDao.existsById(eventId);
        if(isFound) {
            eventDao.deleteById(eventId);
        }
        return isFound;
    }

    public EventImpl findByIdAndTitleAndDate(Long id, String title, Date date) {
        log.info("find event by id={}, title={}, date={}", id, title, date);
        return eventDao.findByIdAndTitleAndDate(id, title, date);
    }
}
