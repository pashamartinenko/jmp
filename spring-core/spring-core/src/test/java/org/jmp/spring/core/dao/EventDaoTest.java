package org.jmp.spring.core.dao;


import org.jmp.spring.core.dao.impl.EventDaoImpl;
import org.jmp.spring.core.model.Event;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import java.util.Map;

class EventDaoTest
{

    private static EventDaoImpl eventDao = new EventDaoImpl();

    @Test
    void getEventById_IdExists_EventIsReturned() {
        EventImpl expectedEvent = new EventImpl(321L, "Title", new Date());
        Storage storage = new Storage();
        storage.setStorageMap(Map.of("event:321", expectedEvent));
        eventDao.setStorage(storage);

        Event event = eventDao.getEventById(expectedEvent.getId());

        Assertions.assertEquals(expectedEvent, event);
    }

    @Test
    void getEventById_IdDoesNotExist_NullIsReturned() {
        long nonExistentId = 321L;
        Storage storage = new Storage();
        storage.setStorageMap(Map.of());
        eventDao.setStorage(storage);
        EventImpl expectedEvent = new EventImpl(211L, "Title 2", new Date());

        Event event = eventDao.getEventById(nonExistentId);

        Assertions.assertNull(event);
    }

    @Test
    void getEventsByTitle_ContainsTitle_ListOfEventsAreReturned() {
        EventImpl expectedEvent = new EventImpl(321L, "2123 Title", new Date());
        EventImpl anExpectedEvent = new EventImpl(232L, "Title 232", new Date());
        EventImpl wrongEvent = new EventImpl(21112L, "Film 232", new Date());
        List<Event> expectedEvents = List.of(expectedEvent, anExpectedEvent);


        Storage storage = new Storage();
        storage.setStorageMap(Map.of("event:321", expectedEvent,
                                     "event:232", anExpectedEvent,
                                     "event:211", wrongEvent));
        eventDao.setStorage(storage);

        List<Event> events = eventDao.getEventsByTitle("Title", 3, 1);
        Assertions.assertAll(() -> {
            Assertions.assertTrue(expectedEvents.size() == events.size());
            Assertions.assertTrue(expectedEvents.containsAll(events));
        });
    }

    @Test
    void getEventsByTitle_TitleDoesNotExist_EmptyList() {
        Storage storage = new Storage();
        storage.setStorageMap(Map.of(
                "event:321", new EventImpl(321L, "2123 Title", new Date()),

                    "event:21112", new EventImpl(21112L, "Film 232", new Date())));
        eventDao.setStorage(storage);

        List<Event> events = eventDao.getEventsByTitle("Non existing title", 2, 1);

        Assertions.assertEquals(List.of(), events);
    }
}
