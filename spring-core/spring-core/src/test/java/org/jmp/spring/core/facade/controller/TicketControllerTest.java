/*
package org.jmp.spring.core.facade.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(locations = {"classpath:webapp/WEB-INF/dispatcher-servlet.xml"})
class TicketControllerTest
{
    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Test
    void test() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/tickets")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test1() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }
}
*/
