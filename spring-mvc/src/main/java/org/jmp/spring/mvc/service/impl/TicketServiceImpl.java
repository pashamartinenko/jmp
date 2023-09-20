package org.jmp.spring.mvc.service.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.mvc.dao.TicketDao;
import org.jmp.spring.mvc.model.impl.TicketImpl;
import org.jmp.spring.mvc.service.TicketService;
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

    public Iterable<TicketImpl> createTickets(List<TicketImpl> tickets) {
        log.info("create tickets {}", tickets);
        return ticketDao.saveAll(tickets);
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
