package org.jmp.spring.core.dao;

import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventDao extends CrudRepository<EventImpl, Long>
{
    List<EventImpl> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<EventImpl> findAllByDate(Date day, Pageable pageable);

    EventImpl findByIdAndTitleAndDate(Long id, String title, Date date);
/*    Event getEventById(long eventId);
    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);
    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);*/
/*    Event updateEvent(Event event);
    boolean deleteEvent(long eventId);*/
}
