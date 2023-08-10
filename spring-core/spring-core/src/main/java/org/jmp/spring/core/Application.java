package org.jmp.spring.core;

import org.jmp.spring.core.facade.BookingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        BookingFacade bf = applicationContext.getBean(BookingFacade.class);
    }
}
