package org.jmp.spring.crud.dao;

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
import java.util.Date;

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
}
