package org.jmp.spring.core.dao;

import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.User;
import java.util.List;

public interface TicketDao
{
    Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category);
    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);
    boolean cancelTicket(long ticketId);
}
