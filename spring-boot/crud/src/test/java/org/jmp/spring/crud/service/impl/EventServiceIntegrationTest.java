package org.jmp.spring.crud.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.inject.Inject;
import org.jmp.spring.crud.dao.EventDao;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.jmp.spring.crud.service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
class EventServiceIntegrationTest
{
    @Inject
    private EventDao eventDao;

    @Inject
    private EventService eventService;

    @AfterEach
    void tearDown() {
        eventDao.deleteAll();
    }

    @Test
    void shouldGetEventById() {
        // GIVEN
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Test event", new Date(), 2000L));

        // WHEN
        EventImpl actualEvent = eventService.getEventById(expectedEvent.getId());

        // THEN
        assertEquals(expectedEvent, actualEvent, "Event by id doesn't match with expected one");
    }

    @Test
    void shouldGetEventsForDay() {
        // GIVEN
        Date expectedDate = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023, Month.APRIL, 30), LocalTime.of(12, 1)));
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Date-based test event", expectedDate, 2000L));
        List<EventImpl> expectedEvents = List.of(expectedEvent);

        // WHEN
        List<EventImpl> actualEvents = eventService.getEventsForDay(expectedDate, 10, 1);

        // THEN
        assertEquals(expectedEvents, actualEvents, "Events for day don't match with expected ones");
    }

    @Test
    void shouldGetEventsByTitle() {
        // GIVEN
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Lorem ipsum", new Date(), 2000L));
        List<EventImpl> expectedEvents = List.of(expectedEvent);

        // WHEN
        List<EventImpl> actualEvents = eventService.getEventsByTitle("Lore", 10, 1);

        // THEN
        assertEquals(expectedEvents, actualEvents, "Events by title don't match with expected ones");
    }

    @Test
    void shouldCreateEvent() {
        // GIVEN
        EventImpl expectedEvent = new EventImpl( "vitae, eleifend", new Date(), 100L);

        // WHEN
        EventImpl actualCreatedEvent = eventService.createEvent(expectedEvent);

        // THEN
        EventImpl actualEventById = eventDao.findById(expectedEvent.getId()).orElse(null);
        assertEquals(expectedEvent, actualCreatedEvent, "Created and expected events don't match");
        assertEquals(expectedEvent, actualEventById, "Created event was not found by getById method");
    }

    @Test
    void shouldUpdateEvent() {
        // GIVEN
        EventImpl event = eventDao.save(new EventImpl( "Test event", new Date(), 1200L));
        EventImpl expectedEvent = new EventImpl(event.getId(), "Updated test event", new Date(),1900L);

        // WHEN
        EventImpl actualEvent = eventService.updateEvent(expectedEvent);

        // THEN
        EventImpl actualEventById = eventDao.findById(event.getId()).orElse(null);
        assertEquals(expectedEvent, actualEvent, "Updated and expected event don't match");
        assertEquals(expectedEvent, actualEventById, "Updated and expected event don't match");
        assertEquals(expectedEvent.getPrice(), actualEvent.getPrice());
        assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        assertEquals(expectedEvent.getDate(), actualEvent.getDate());
    }

    @Test
    void shouldDeleteEvent() {
        // GIVEN
        EventImpl event = eventDao.save(new EventImpl( "Test event", new Date(), 1200L));

        // WHEN
        boolean isDeleted = eventService.deleteEvent(event.getId());

        // THEN
        boolean isPresent = eventDao.existsById(event.getId());
        assertTrue(isDeleted);
        assertFalse(isPresent);
    }
}
