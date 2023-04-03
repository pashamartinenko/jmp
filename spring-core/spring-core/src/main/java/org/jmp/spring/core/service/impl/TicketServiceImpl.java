package org.jmp.spring.core.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.TicketDao;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.service.TicketService;
import java.util.List;

@Slf4j
@Setter
public class TicketServiceImpl implements TicketService
{

    private TicketDao ticketDao;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        log.info("book ticket by userId {}, eventId {}, place {}, category {}", userId, eventId, place, category);
        return ticketDao.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.info("book ticket for user {}", user);
        return ticketDao.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("book ticket for event {}", event);
        return ticketDao.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        log.info("cancel ticket with ticketId {}", ticketId);
        return ticketDao.cancelTicket(ticketId);
    }
}
