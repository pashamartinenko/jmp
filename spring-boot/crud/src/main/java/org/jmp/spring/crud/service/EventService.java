package org.jmp.spring.crud.service;

import org.jmp.spring.crud.model.impl.EventImpl;
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
}
