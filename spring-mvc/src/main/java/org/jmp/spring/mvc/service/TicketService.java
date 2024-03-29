package org.jmp.spring.mvc.service;

import org.jmp.spring.mvc.model.impl.TicketImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TicketService
{
    TicketImpl createTicket(TicketImpl ticket);
    Iterable<TicketImpl> createTickets(List<TicketImpl> tickets);
    List<TicketImpl> findByUserId(Long userId, Pageable pageable);
    List<TicketImpl> findByEventId(Long eventId, Pageable pageable);
    boolean cancelTicket(long ticketId);
}