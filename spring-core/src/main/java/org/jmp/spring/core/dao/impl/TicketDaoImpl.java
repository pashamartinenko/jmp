package org.jmp.spring.core.dao.impl;

import static java.util.Comparator.comparing;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.EventDao;
import org.jmp.spring.core.dao.TicketDao;
import org.jmp.spring.core.dao.UserDao;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.storage.Storage;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Setter
public class TicketDaoImpl implements TicketDao
{
    public static final String TICKET_PREFIX = "ticket:";

    private Storage storage;
    private EventDao eventDao;
    private UserDao userDao;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        List<Ticket> bookedTickets = getAllBookedTickets();

        bookedTickets.stream()
                .filter(ticket -> ticket.getEventId() == eventId)
                .filter(ticket -> ticket.getPlace() == place)
                .findAny()
                .ifPresent(ticket -> {
                    log.info("Ticket already exists {}", ticket);
                    throw new IllegalStateException("This place has already been booked");
                });

        long ticketId = new Random().nextLong();
        Ticket ticket = new TicketImpl(ticketId, eventId, userId, category, place);
        storage.getStorageMap().put(TICKET_PREFIX + ticketId, ticket);

        log.info("Ticket has been created {}", ticket);

        return ticket;
    }
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return getAllBookedTickets().stream()
                .filter(ticket -> user.getId() == ticket.getUserId())
                .sorted(comparing(ticket -> eventDao.getEventById(ticket.getEventId()).getDate(), Comparator.reverseOrder()))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .toList();
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return getAllBookedTickets().stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .sorted(comparing(ticket -> userDao.getUserById(ticket.getUserId()).getEmail()))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .toList();
    }

    public boolean cancelTicket(long ticketId) {
        String key = TICKET_PREFIX + ticketId;
        Map<String, Object> st = storage.getStorageMap();
        if(st.containsKey(key)) {
            st.remove(key);
            return true;
        }
        return false;
    }

    private List<Ticket> getAllBookedTickets() {
        return storage.getStorageMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(TICKET_PREFIX))
                .map(entry -> (Ticket) entry.getValue())
                .toList();
    }
}
