/*
package org.jmp.spring.core.facade.impl;

import lombok.RequiredArgsConstructor;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.service.EventService;
import org.jmp.spring.core.service.TicketService;
import org.jmp.spring.core.service.UserService;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class BookingFacadeImpl implements BookingFacade
{

    private final UserService userService;
    private final EventService eventService;
    private final TicketService ticketService;

    @Override
    public Event getEventById(long eventId)
    {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum)
    {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum)
    {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event)
    {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event)
    {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId)
    {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId)
    {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email)
    {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum)
    {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user)
    {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user)
    {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId)
    {
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category)
    {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum)
    {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum)
    {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId)
    {
        return ticketService.cancelTicket(ticketId);
    }
}
*/
