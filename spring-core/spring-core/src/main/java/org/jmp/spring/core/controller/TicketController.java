package org.jmp.spring.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketController
{
    @RequestMapping("/tickets")
    public String bookTicket() {
        return "home";
    }
}

