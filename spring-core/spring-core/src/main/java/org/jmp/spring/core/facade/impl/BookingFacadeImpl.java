package org.jmp.spring.core.facade.impl;

import lombok.RequiredArgsConstructor;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserAccount;
import org.jmp.spring.core.model.impl.UserImpl;
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

    @Override
    public UserImpl getUserById(long userId)
    {
        return userService.getUserById(userId);
    }

    @Override
    public UserImpl getUserByEmail(String email)
    {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<UserImpl> getUsersByName(String name, int pageSize, int pageNum)
    {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public UserImpl createUser(UserImpl user)
    {
        return userService.createUser(user);
    }

    @Override
    public UserImpl updateUser(UserImpl user)
    {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId)
    {
        return userService.deleteUser(userId);
    }

    @Override
    public TicketImpl bookTicket(long userId, long eventId, int place, Ticket.Category category)
    {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<TicketImpl> getBookedTickets(UserImpl user, int pageSize, int pageNum)
    {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<TicketImpl> getBookedTickets(EventImpl event, int pageSize, int pageNum)
    {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId)
    {
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public UserImpl refillUserAccount(UserImpl user, UserAccount userAccount)
    {

        return userService.refillUserAccount(user, userAccount);
    }
}
