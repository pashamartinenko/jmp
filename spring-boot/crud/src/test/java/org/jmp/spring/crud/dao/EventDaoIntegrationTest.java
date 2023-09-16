package org.jmp.spring.crud.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmp.spring.crud.model.Event;
import org.jmp.spring.crud.model.impl.EventImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EventDaoIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EventDao eventDao;

    @Test
    void save_EventPersisted() {
        // GIVEN
        EventImpl expectedEvent = new EventImpl( "Test Event", new Date(), 2000L);

        // WHEN
        eventDao.save(expectedEvent);

        // THEN
        Event actualEvent = entityManager.find(EventImpl.class, expectedEvent.getId());
        assertEquals(expectedEvent, actualEvent);
    }


    @Test
    void deleteById_EventExists_EventDeleted() {
        // GIVEN
        Event expectedEvent = new EventImpl( "Test Event", new Date(), 2000L);
        entityManager.persist(expectedEvent);
        entityManager.flush();

        // WHEN
        eventDao.deleteById(expectedEvent.getId());

        // THEN
        Event actualEvent = entityManager.find(EventImpl.class, expectedEvent.getId());
        assertNull(actualEvent, "Null is expected when the event is deleted");
    }

    @Test
    void deleteById_EventNotExists_NullReturned() {
        // GIVEN
        Long nonExistentId = 9999L;

        // WHEN
        eventDao.deleteById(nonExistentId);

        // THEN
        Event actualEvent = entityManager.find(EventImpl.class, nonExistentId);
        assertNull(actualEvent, "Null is expected when removing by non existent id");
    }

    @Test
    void existsById_EventExists_TrueReturned() {
        // GIVEN
        Event expectedEvent = new EventImpl( "Test Event", new Date(), 2000L);
        entityManager.persist(expectedEvent);
        entityManager.flush();

        // WHEN
        boolean existsById = eventDao.existsById(expectedEvent.getId());

        // THEN
        assertTrue(existsById, "True is expected when the event exists");
    }

    @Test
    void existsById_EventNotExists_FalseReturned() {
        // GIVEN
        Long nonExistentId = 9999L;

        // WHEN
        boolean existsById = eventDao.existsById(nonExistentId);

        // THEN
        assertFalse(existsById, "False is expected when the event is absent");
    }

    @Test
    void findById_EventsExists_EventReturned() {
        // GIVEN
        Event expectedEvent = new EventImpl( "Test Event", new Date(), 2000L);
        entityManager.persist(expectedEvent);
        entityManager.flush();

        // WHEN
        EventImpl actualEvent = eventDao.findById(expectedEvent.getId()).orElse(null);

        // THEN
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    void findById_EventsNotExists_NullReturned() {

        EventImpl actualEvent = eventDao.findById(1L).orElse(null);

        assertNull(actualEvent, "Null should be returned if event doesn't exist");
    }


    @Test
    void findAllByTitleContainingIgnoreCase_EventsExists_EventsReturned() {
        // GIVEN
        Event expectedEvent1 = new EventImpl( "Test Event 1", new Date(), 2000L);
        Event expectedEvent2 = new EventImpl( "Test Event 2", new Date(), 100L);
        entityManager.persist(expectedEvent1);
        entityManager.persist(expectedEvent2);
        entityManager.flush();

        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByTitleContainingIgnoreCase("test", PageRequest.of(0, 2));

        // THEN
        assertEquals(List.of(expectedEvent1, expectedEvent2), actualEvents);
    }

    @Test
    void findAllByTitleContainingIgnoreCase_EventsExistsAndPaging_PartialEventsReturned() {
        // GIVEN
        Event expectedEvent1 = new EventImpl( "Test Event 1", new Date(), 2000L);
        Event expectedEvent2 = new EventImpl( "Test Event 2", new Date(), 100L);
        Event expectedEvent3 = new EventImpl( "Test Event 3", new Date(), 999L);
        entityManager.persist(expectedEvent1);
        entityManager.persist(expectedEvent2);
        entityManager.persist(expectedEvent3);
        entityManager.flush();

        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByTitleContainingIgnoreCase("test", PageRequest.of(1, 2));

        // THEN
        assertEquals(List.of(expectedEvent3), actualEvents);
    }

    @Test
    void findAllByTitleContainingIgnoreCase_EventsNotExists_EmptyListReturned() {
        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByTitleContainingIgnoreCase("test", PageRequest.of(0, 100));

        // THEN
        assertThat(actualEvents).isEmpty();
    }

    @Test
    void findAllByDate_EventsExists_EventsReturned() {
        // GIVEN
        Date expectedDate = new Date();
        Event expectedEvent1 = new EventImpl( "Test Event 1", expectedDate, 2000L);
        Event expectedEvent2 = new EventImpl( "Test Event 2", expectedDate, 100L);
        entityManager.persist(expectedEvent1);
        entityManager.persist(expectedEvent2);
        entityManager.flush();

        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByDate(expectedDate, PageRequest.of(0, 2));

        // THEN
        assertEquals(List.of(expectedEvent1, expectedEvent2), actualEvents);
    }

    @Test
    void findAllByDate_EventsExistsAndPaging_PartialEventsReturned() {
        // GIVEN
        Date expectedDate = new Date();
        Event expectedEvent1 = new EventImpl( "Test Event 1", expectedDate, 2000L);
        Event expectedEvent2 = new EventImpl( "Test Event 2", expectedDate, 100L);
        Event expectedEvent3 = new EventImpl( "Test Event 3", expectedDate, 999L);
        entityManager.persist(expectedEvent1);
        entityManager.persist(expectedEvent2);
        entityManager.persist(expectedEvent3);
        entityManager.flush();

        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByDate(expectedDate, PageRequest.of(2, 1));

        // THEN
        assertEquals(List.of(expectedEvent3), actualEvents);
    }

    @Test
    void findAllByDate_EventsNotExists_EmptyListReturned() {
        // GIVEN
        Date expectedDate = new Date();

        // WHEN
        List<EventImpl> actualEvents = eventDao.findAllByDate(expectedDate, PageRequest.of(0, 100));

        // THEN
        assertThat(actualEvents).isEmpty();
    }
}
