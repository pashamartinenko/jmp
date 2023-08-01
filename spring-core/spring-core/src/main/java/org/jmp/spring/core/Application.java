package org.jmp.spring.core;

import org.jmp.spring.core.controller.EventController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("src/main/WEB-INF/config.xml");
        EventController bean = context.getBean(EventController.class);
        System.out.println();
    }
}
