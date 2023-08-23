package org.jmp.spring.core.service;

import org.jmp.spring.core.model.impl.TicketImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TicketService
{
    TicketImpl createTicket(TicketImpl ticket);
    List<TicketImpl> findByUserId(Long userId, Pageable pageable);
    List<TicketImpl> findByEventId(Long eventId, Pageable pageable);
    boolean cancelTicket(long ticketId);
}