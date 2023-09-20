package org.jmp.spring.mvc.service;

import org.jmp.spring.mvc.model.impl.EventImpl;

import java.util.Date;
import java.util.List;

public interface EventService
{
     EventImpl createEvent(EventImpl event);
     EventImpl getEventById(long eventId);
    List<EventImpl> getEventsByTitle(String title, int pageSize, int pageNum);
    List<EventImpl> getEventsForDay(Date day, int pageSize, int pageNum);
     EventImpl updateEvent(EventImpl event);
    boolean deleteEvent(long eventId);
    EventImpl findByIdAndTitleAndDate(Long id, String title, Date date);
}
