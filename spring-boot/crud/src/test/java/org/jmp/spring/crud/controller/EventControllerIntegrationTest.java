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

    /*@MockBean
    private SecurityConfiguration securityConfiguration;*/
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
    void getEventsByTitle_EventsExist_EventsReturned() throws Exception
    {
        // GIVEN
        Date expectedDate = new Date();
        EventImpl expectedEvent1 = new EventImpl( "Test Event 1", expectedDate, 2000L);
        EventImpl expectedEvent2 = new EventImpl( "Test Event 2", expectedDate, 100L);
        EventImpl expectedEvent3 = new EventImpl( "Another event", expectedDate, 100L);
        eventDao.save(expectedEvent1);
        eventDao.save(expectedEvent2);
        eventDao.save(expectedEvent3);
        String dateString = formatter.format(expectedDate);

        // WHEN-THEN
        mvc.perform(get("/v1/events?title=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedEvent1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(expectedEvent1.getTitle())))
                .andExpect(jsonPath("$[0].date", is(dateString)))
                .andExpect(jsonPath("$[0].price", is(expectedEvent1.getPrice().intValue())))
                .andExpect(jsonPath("$[1].id", is(expectedEvent2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(expectedEvent2.getTitle())))
                .andExpect(jsonPath("$[1].date", is(dateString)))
                .andExpect(jsonPath("$[1].price", is(expectedEvent2.getPrice().intValue())));
    }

    @Test
    void getEventsForDay_EventsExist_EventsReturned() throws Exception {
        // GIVEN
        Date expectedDate = formatter.parse("2018-06-12 13:00:00");
        EventImpl expectedEvent1 = new EventImpl( "Test Event 1", expectedDate, 2000L);
        EventImpl expectedEvent2 = new EventImpl( "Test Event 2", new Date(), 100L);
        EventImpl expectedEvent3 = new EventImpl( "Another event", expectedDate, 100L);
        eventDao.save(expectedEvent1);
        eventDao.save(expectedEvent2);
        eventDao.save(expectedEvent3);
        String dateString = formatter.format(expectedDate);

        // WHEN-THEN
        mvc.perform(get("/v1/events?day={day}", dateString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedEvent1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(expectedEvent1.getTitle())))
                .andExpect(jsonPath("$[0].date", is(dateString)))
                .andExpect(jsonPath("$[0].price", is(expectedEvent1.getPrice().intValue())))
                .andExpect(jsonPath("$[1].id", is(expectedEvent3.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(expectedEvent3.getTitle())))
                .andExpect(jsonPath("$[1].date", is(dateString)))
                .andExpect(jsonPath("$[1].price", is(expectedEvent3.getPrice().intValue())));
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
