package org.jmp.spring.core;

import org.jmp.spring.core.facade.BookingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");
        BookingFacade bookingFacade = applicationContext.getBean(BookingFacade.class);
        bookingFacade.getEventById(123L);
        bookingFacade.getEventsByTitle("Title", 1, 1);
    }
}
