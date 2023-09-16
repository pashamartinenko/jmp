package org.jmp.spring.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.jmp.spring.crud.dao.EventDao;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class EventServiceImplTest
{

    private static EventDao eventDao = Mockito.mock(EventDao.class);

    private static EventServiceImpl eventService = new EventServiceImpl();

    private static EventImpl expectedEvent;

    @BeforeAll
    static void setUp() throws ParseException
    {
        eventService.setEventDao(eventDao);
        String dateString = "01-06-2023 21:00:00";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date testDate = formatter.parse(dateString);
        expectedEvent = new EventImpl(1L, "Test Event", testDate, 2000L);
    }

    @Test
    void getEventById_EventExists_ReturnEvent()
    {
        // given
        when(eventDao.findById(expectedEvent.getId()))
                .thenReturn(Optional.of(expectedEvent));

        // WHEN
        EventImpl actualEvent = eventService.getEventById(expectedEvent.getId());

        // THEN
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    void getEventById_EventNotExists_ThrowException()
    {
        // GIVEN
        when(eventDao.findById(expectedEvent.getId())).thenReturn(Optional.empty());

        // WHEN
        Executable executable = () ->  eventService.getEventById(expectedEvent.getId());

        // THEN
        RuntimeException actualException = assertThrows(RuntimeException.class, executable);
        assertEquals("Event with id=1 does not exist", actualException.getMessage());
    }

    @Test
    void getEventsByTitle_EventExists_ReturnEvent() {
        // GIVEN
        when(eventDao.findAllByTitleContainingIgnoreCase(eq(expectedEvent.getTitle()), any()))
                .thenReturn(List.of(expectedEvent));

        // WHEN
        List<EventImpl> actualEvents = eventService.getEventsByTitle(expectedEvent.getTitle(), 1, 1);

        // THEN
        assertEquals(List.of(expectedEvent), actualEvents);
    }

    @Test
    void getEventsForDay_EventExists_ReturnEvent() {
        // GIVEN
        when(eventDao.findAllByDate(eq(expectedEvent.getDate()), any()))
                .thenReturn(List.of(expectedEvent));

        // WHEN
        List<EventImpl> actualEvents = eventService.getEventsForDay(expectedEvent.getDate(), 1, 1);

        // THEN
        assertEquals(List.of(expectedEvent), actualEvents);
    }

    @Test
    void createEvent_EventCreated() {
        // GIVEN
        when(eventDao.save(expectedEvent)).thenReturn(expectedEvent);

        // WHEN
        EventImpl actualEvent = eventService.createEvent(expectedEvent);

        // THEN
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    void updateEvent_EventsExists_EventUpdated() {
        // GIVEN
        when(eventDao.save(expectedEvent)).thenReturn(expectedEvent);
        when(eventDao.findById(expectedEvent.getId()))
                .thenReturn(Optional.of(expectedEvent));

        // WHEN
        EventImpl actualEvent = eventService.updateEvent(expectedEvent);

        // THEN
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    void updateEvent_EventsNotExists_ThrowException() {
        // WHEN
        Executable executable = () ->  eventService.updateEvent(expectedEvent);

        // THEN
        RuntimeException actualException = assertThrows(RuntimeException.class, executable);
        assertEquals("Event with id=1 does not exist", actualException.getMessage());
    }

    @Test
    void deleteEvent_EventExists_ReturnTrue() {
        // GIVEN
        when(eventDao.existsById(expectedEvent.getId()))
                .thenReturn(true);

        // WHEN
        boolean isDeleted = eventService.deleteEvent(expectedEvent.getId());

        // THEN
        assertTrue(isDeleted, "True was expected when event is deleted");
    }

    @Test
    void deleteEvent_EventNotExists_ReturnFalse() {
        // GIVEN
        when(eventDao.existsById(expectedEvent.getId()))
                .thenReturn(false);

        // WHEN
        boolean isDeleted = eventService.deleteEvent(expectedEvent.getId());

        // THEN
        assertFalse(isDeleted, "False was expected when event doesn't exist");
    }
}
