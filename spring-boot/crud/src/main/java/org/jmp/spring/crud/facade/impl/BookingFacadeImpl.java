package org.jmp.spring.crud.facade.impl;

import lombok.RequiredArgsConstructor;
import org.jmp.spring.crud.facade.BookingFacade;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.jmp.spring.crud.service.EventService;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingFacadeImpl implements BookingFacade
{

    private final EventService eventService;

    @Override
    public EventImpl getEventById(long eventId)
    {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<EventImpl> getEventsByTitle(String title, int pageSize, int pageNum)
    {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<EventImpl> getEventsForDay(Date day, int pageSize, int pageNum)
    {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public EventImpl createEvent(EventImpl event)
    {
        return eventService.createEvent(event);
    }

    @Override
    public EventImpl updateEvent(EventImpl event)
    {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId)
    {
        return eventService.deleteEvent(eventId);
    }

}
