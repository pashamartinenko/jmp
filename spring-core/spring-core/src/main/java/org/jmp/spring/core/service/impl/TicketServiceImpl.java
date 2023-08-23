package org.jmp.spring.core.service.impl;

import static java.lang.String.format;

import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.TicketDao;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import org.jmp.spring.core.service.EventService;
import org.jmp.spring.core.service.TicketService;
import org.jmp.spring.core.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;

@Slf4j
@Setter
public class TicketServiceImpl implements TicketService
{
    private UserService userService;
    private EventService eventService;
    private TicketDao ticketDao;

    @Transactional
    public TicketImpl bookTicket(Long userId, Long eventId, Integer place, Ticket.Category category) {
        log.info("book ticket by userId {}, eventId {}, place {}, category {}", userId, eventId, place, category);
        UserImpl userById = userService.getUserById(userId);
        EventImpl eventById = eventService.getEventById(eventId);

        eventById.getTickets().stream()
                .filter(ticket -> ticket.getPlace() == place)
                .findAny()
                .ifPresent(ticket -> {
                    log.info("Ticket already exists {}", ticket);
                    throw new IllegalStateException(format("The place=%d for the event with id=%d has already been booked", place, eventId));
                });

        if (userById.getUserAccount() == null || userById.getUserAccount().getBalance() < eventById.getPrice()) {
            throw new IllegalStateException(format("Not enough balance for user with id=%d to book a ticket for eventId=%d. " +
                    "Event price = %d, user balance = %d", userId, eventId, eventById.getPrice(), userById.getUserAccount().getBalance()));
        }

        userById.getUserAccount().withdrawBalance(eventById.getPrice());
        userService.updateUser(userById);

        TicketImpl ticket = new TicketImpl(category, place, eventById, userById);
        return ticketDao.save(ticket);
    }

    public List<TicketImpl> getBookedTickets(UserImpl user, int pageSize, int pageNum) {
        log.info("get booked tickets for user {}", user);
        UserImpl existingUser = userService.findByIdAndNameAndEmail(user.getId(), user.getName(), user.getEmail());
        if (existingUser == null)
        {
            throw new IllegalStateException(format("User with name=%s and email=%s does not exist", user.getName(), user.getEmail()));
        }
        return ticketDao.findByUserId(existingUser.getId(), PageRequest.of(pageNum - 1, pageSize, Sort.by("event.date").descending()));
    }

    public List<TicketImpl> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("book ticket for event {}", event);
        EventImpl existingEvent = eventService.findByIdAndTitleAndDate(event.getId(), event.getTitle(), event.getDate());
        if (existingEvent == null)
        {
            throw new IllegalStateException(format("Event with id=%d, title=%s and date=%s does not exist", event.getId(),
                    event.getTitle(), event.getDate()));
        }
        return ticketDao.findByEventId(existingEvent.getId(), PageRequest.of(pageNum - 1, pageSize, Sort.by("user.email")));
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
