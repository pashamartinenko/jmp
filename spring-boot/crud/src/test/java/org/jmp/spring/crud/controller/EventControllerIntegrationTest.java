package org.jmp.spring.crud.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmp.spring.crud.dao.EventDao;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.text.SimpleDateFormat;
import java.util.Date;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class EventControllerIntegrationTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventController eventController;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SimpleDateFormat formatter;

    @AfterEach
    void tearDown() {
        eventDao.deleteAll();
    }

    @Test
    void getEventById_EventExists_EventReturned() throws Exception {
        // GIVEN
        Date expectedDate = new Date();
        EventImpl expectedEvent = new EventImpl( "Test Event 1", expectedDate, 2000L);
        eventDao.save(expectedEvent);
        String dateString = formatter.format(expectedEvent.getDate());

        // WHEN-THEN
        mvc.perform(get("/v1/events/{id}", expectedEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedEvent.getId().intValue())))
                .andExpect(jsonPath("$.title", is(expectedEvent.getTitle())))
                .andExpect(jsonPath("$.date", is(dateString)))
                .andExpect(jsonPath("$.price", is(expectedEvent.getPrice().intValue())));
    }

    @Test
    void createEvent_EventCreated() throws Exception
    {
        // GIVEN
        Date expectedDate = new Date();
        EventImpl expectedEvent = new EventImpl( "Test Event 1", expectedDate, 2000L);
        String dateString = formatter.format(expectedEvent.getDate());

        // WHEN-THEN
        mvc.perform(post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(expectedEvent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(expectedEvent.getTitle())))
                .andExpect(jsonPath("$.date", is(dateString)))
                .andExpect(jsonPath("$.price", is(expectedEvent.getPrice().intValue())));

    }

    @Test
    void updateEvent_EventUpdated() throws Exception {
        // GIVEN
        Date expectedDate = new Date();
        EventImpl existingEvent = new EventImpl( "Test Event 1", formatter.parse("2018-06-12 13:00:00"), 2000L);
        eventDao.save(existingEvent);
        EventImpl expectedEvent = new EventImpl( "Test Update Event", expectedDate, 1200L);
        String dateString = formatter.format(expectedEvent.getDate());

        // WHEN-THEN
        mvc.perform(put("/v1/events/{id}", existingEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(expectedEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingEvent.getId().intValue())))
                .andExpect(jsonPath("$.title", is(expectedEvent.getTitle())))
                .andExpect(jsonPath("$.date", is(dateString)))
                .andExpect(jsonPath("$.price", is(expectedEvent.getPrice().intValue())));
    }

    @Test
    void deleteEvent_EventDeleted() throws Exception {
        // GIVEN
        Date expectedDate = new Date();
        EventImpl expectedEvent = new EventImpl( "Test Event 1", expectedDate, 2000L);
        eventDao.save(expectedEvent);
        String dateString = formatter.format(expectedEvent.getDate());

        // WHEN-THEN
        mvc.perform(delete("/v1/events/{id}", expectedEvent.getId()))
                .andExpect(status().isNoContent());

        EventImpl actualEvent = eventDao.findById(expectedEvent.getId()).orElse(null);
        assertNull(actualEvent, "Expected null: the event should have been deleted from the persistence");
    }
}
