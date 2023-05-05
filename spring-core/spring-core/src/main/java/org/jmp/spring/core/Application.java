package org.jmp.spring.core;

import static java.lang.String.format;

import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        BookingFacade bf = applicationContext.getBean(BookingFacade.class);
        LocalDateTime dunaDateTime = LocalDateTime.of(LocalDate.of(2023, Month.APRIL, 30), LocalTime.of(12, 1));
        EventImpl event = new EventImpl(2L, "Duna", Timestamp.valueOf(dunaDateTime), 2900L);
        List<TicketImpl> bookedTickets = bf.getBookedTickets(event, 10, 1);
        UserImpl user = new UserImpl(152L, "Hello", "pavel@mail.com");
        bf.bookTicket(user.getId(), event.getId(), 38, Ticket.Category.BAR);
        System.out.println(bookedTickets);
        System.out.println();
    }
}
