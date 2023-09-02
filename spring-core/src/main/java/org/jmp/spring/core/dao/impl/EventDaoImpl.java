package org.jmp.spring.core.dao.impl;

import lombok.Setter;
import org.jmp.spring.core.dao.EventDao;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.storage.Storage;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Setter
public class EventDaoImpl implements EventDao
{
    public static final String EVENT_PREFIX = "event:";
    private Storage storage;
    public Event getEventById(long eventId) {
        return (Event) storage.getStorageMap().get(EVENT_PREFIX + eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return storage.getStorageMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(EVENT_PREFIX))
                .map(entry -> (Event) entry.getValue())
                .filter(event -> event.getTitle().contains(title))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .toList();
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return storage.getStorageMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("event"))
                .map(entry -> (Event) entry.getValue())
                .filter(event -> event.getDate().equals(day))
                .skip((long) pageNum * pageSize)
                .limit(pageSize)
                .toList();
    }

    public Event createEvent(Event event) {
        event.setId(new Random().nextLong());
        storage.getStorageMap().put(EVENT_PREFIX + event.getId(), event);
        return event;
    }

    public Event updateEvent(Event event) {
        Event anEvent = getEventById(event.getId());
        anEvent.setTitle(event.getTitle());
        anEvent.setDate(event.getDate());
        storage.getStorageMap().put(EVENT_PREFIX + anEvent.getId(), anEvent);
        return anEvent;
    }

    public boolean deleteEvent(long eventId) {
        String key = EVENT_PREFIX + eventId;
        Map<String, Object> st = storage.getStorageMap();
        if(st.containsKey(key)) {
            st.remove(key);
            return true;
        }
        return false;
    }
}
