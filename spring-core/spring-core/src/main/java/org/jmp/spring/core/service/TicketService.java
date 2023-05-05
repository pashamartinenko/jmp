package org.jmp.spring.core.service;

import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import java.util.List;

public interface TicketService
{
    TicketImpl bookTicket(long userId, long eventId, int place, Ticket.Category category);
    List<TicketImpl> getBookedTickets(UserImpl user, int pageSize, int pageNum);
    List<TicketImpl> getBookedTickets(Event event, int pageSize, int pageNum);
    boolean cancelTicket(long ticketId);
}
