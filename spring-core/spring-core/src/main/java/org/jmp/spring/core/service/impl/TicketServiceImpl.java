package org.jmp.spring.core.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.TicketDao;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.service.TicketService;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Slf4j
@Setter
public class TicketServiceImpl implements TicketService
{
    private TicketDao ticketDao;

    public TicketImpl createTicket(TicketImpl ticket) {
        log.info("create ticket {}", ticket);
        return ticketDao.save(ticket);
    }

    public List<TicketImpl> findByUserId(Long userId, Pageable pageable) {
        log.info("get tickets by user id {}", userId);
        return ticketDao.findByUserId(userId, pageable);
    }

    public List<TicketImpl> findByEventId(Long eventId, Pageable pageable) {
        log.info("get tickets by event id {}", eventId);
        return ticketDao.findByEventId(eventId, pageable);
    }

    public boolean cancelTicket(long ticketId) {
        log.info("remove ticket with ticketId {}", ticketId);
        boolean isFound = ticketDao.existsById(ticketId);
        if (isFound) {
            ticketDao.deleteById(ticketId);
        }
        return isFound;
    }
}
