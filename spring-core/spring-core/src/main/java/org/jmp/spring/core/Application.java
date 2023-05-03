package org.jmp.spring.core;

import static java.lang.String.format;

import org.jmp.spring.core.dao.UserDao;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import org.jmp.spring.core.service.EventService;
import org.jmp.spring.core.service.TicketService;
import org.jmp.spring.core.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        Random random = new Random();
        long rand = random.nextInt();
        System.out.println(rand);

/*        UserService userService = applicationContext.getBean(UserService.class);
        //User createdUser = userService.createUser(new UserImpl(format("Hello%d", rand), format("pavel%d@mail.com", rand)));
        User userById = userService.getUserById(152L);
        System.out.println(userById);
        User userByEmail = userService.getUserByEmail(userById.getEmail());
        System.out.println(userByEmail);
        boolean b = userService.deleteUser(1L);
        System.out.println(b);
        UserImpl userToUpdate = new UserImpl("2new name", "2updated@mail.com");
        userToUpdate.setId(202L);
        userService.updateUser(userToUpdate);
        List<? extends User> users = userService.getUsersByName("hEllO", 3, 1);
        System.out.println(users);*/

/*        EventService eventService = applicationContext.getBean(EventService.class);
        //eventService.createEvent(new EventImpl("121 event", new Date()));
        Event eventById = eventService.getEventById(52L);
        System.out.println(eventById);
        List<? extends Event> eventsByTitle = eventService.getEventsByTitle("EvEnt", 7, 1);
        System.out.println(eventsByTitle);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
        List<? extends Event> eventsForDay = eventService.getEventsForDay(Timestamp.valueOf(dateTime), 2, 1);
        System.out.println(eventsForDay);
        eventService.updateEvent(new EventImpl(202L, "Titanic", new Date()));
*//*        boolean b = eventService.deleteEvent(52L);
        System.out.println(b);*/

/*        TicketService ticketService = applicationContext.getBean(TicketService.class);
        //ticketService.bookTicket(152L, 2L, 26, Ticket.Category.BAR);
        List<TicketImpl> tickets = ticketService.getBookedTickets(new UserImpl(152L, "Hello", "pavel@mail.com"), 2, 1);
        System.out.println();
        LocalDateTime dunaDateTime = LocalDateTime.of(LocalDate.of(2023, Month.APRIL, 30), LocalTime.of(12, 1));
        List<TicketImpl> ticketsByEvent = ticketService.getBookedTickets(new EventImpl(2L, "Duna", Timestamp.valueOf(dunaDateTime)), 10, 1);
        System.out.println();

        ticketService.cancelTicket(452L);
        System.out.println();*/

        BookingFacade bf = applicationContext.getBean(BookingFacade.class);
        LocalDateTime dunaDateTime = LocalDateTime.of(LocalDate.of(2023, Month.APRIL, 30), LocalTime.of(12, 1));
        List<TicketImpl> bookedTickets = bf.getBookedTickets(new EventImpl(2L, "Duna", Timestamp.valueOf(dunaDateTime)), 10, 1);
        System.out.println(bookedTickets);
        System.out.println();
    }
}
