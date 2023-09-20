package org.jmp.spring.mvc.facade.impl;

import static java.lang.String.format;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.mvc.facade.BookingFacade;
import org.jmp.spring.mvc.model.Ticket;
import org.jmp.spring.mvc.model.impl.EventImpl;
import org.jmp.spring.mvc.model.impl.TicketImpl;
import org.jmp.spring.mvc.model.impl.UserAccount;
import org.jmp.spring.mvc.model.impl.UserImpl;
import org.jmp.spring.mvc.model.impl.xml.Tickets;
import org.jmp.spring.mvc.service.EventService;
import org.jmp.spring.mvc.service.TicketService;
import org.jmp.spring.mvc.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.oxm.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BookingFacadeImpl implements BookingFacade
{

    private final UserService userService;
    private final EventService eventService;
    private final TicketService ticketService;
    private final Unmarshaller unmarshaller;

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
    @Transactional
    public TicketImpl bookTicket(Long userId, Long eventId, Integer place, Ticket.Category category)
    {
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
        return ticketService.createTicket(ticket);
    }

    @Override
    public List<TicketImpl> getBookedTickets(UserImpl user, int pageSize, int pageNum)
    {
        log.info("get booked tickets for user {}", user);
        UserImpl existingUser = userService.findByIdAndNameAndEmail(user.getId(), user.getName(), user.getEmail());
        if (existingUser == null)
        {
            throw new IllegalStateException(format("User with name=%s and email=%s does not exist", user.getName(), user.getEmail()));
        }
        return ticketService.findByUserId(existingUser.getId(), PageRequest.of(pageNum - 1, pageSize, Sort.by("event.date").descending()));
    }

    @Override
    public List<TicketImpl> getBookedTickets(EventImpl event, int pageSize, int pageNum)
    {
        log.info("book ticket for event {}", event);
        EventImpl existingEvent = eventService.findByIdAndTitleAndDate(event.getId(), event.getTitle(), event.getDate());
        if (existingEvent == null)
        {
            throw new IllegalStateException(format("Event with id=%d, title=%s and date=%s does not exist", event.getId(),
                    event.getTitle(), event.getDate()));
        }
        return ticketService.findByEventId(existingEvent.getId(), PageRequest.of(pageNum - 1, pageSize, Sort.by("user.email")));
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
    @Override
    @Transactional
    public void preloadTickets()
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource("tickets.xml");
        Tickets xmlTickets;

        try (FileInputStream is = new FileInputStream(resource.getFile()))
        {
            xmlTickets = (Tickets) unmarshaller.unmarshal(new StreamSource(is));

        }
        catch (IOException e)
        {
            throw new IllegalStateException(e);
        }

        List<TicketImpl> tickets = xmlTickets.getTickets();
        tickets.forEach(ticket -> {
            userService.createUser(ticket.getUser());
            eventService.createEvent(ticket.getEvent());
        });
        ticketService.createTickets(tickets);
    }
}