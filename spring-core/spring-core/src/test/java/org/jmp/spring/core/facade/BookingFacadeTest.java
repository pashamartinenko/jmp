package org.jmp.spring.core.facade;

import jakarta.inject.Inject;
import org.jmp.spring.core.dao.EventDao;
import org.jmp.spring.core.dao.TicketDao;
import org.jmp.spring.core.dao.UserDao;
import org.jmp.spring.core.model.Ticket;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserAccount;
import org.jmp.spring.core.model.impl.UserImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

@SpringJUnitConfig(locations = {"classpath:/config.xml"})
class BookingFacadeTest
{
    @Inject
    private EventDao eventDao;

    @Inject
    private UserDao userDao;

    @Inject
    private TicketDao ticketDao;

    @Inject
    private BookingFacade bookingFacade;

    @AfterEach
    void tearDown() {
        ticketDao.deleteAll();
        userDao.deleteAll();
        eventDao.deleteAll();
    }

    @Test
    void shouldGetEventById() {
        // GIVEN
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Test event", new Date(), 2000L));

        // WHEN
        EventImpl actualEvent = bookingFacade.getEventById(expectedEvent.getId());

        // THEN
        Assertions.assertEquals(expectedEvent, actualEvent, "Event by id doesn't match with expected one");
    }

    @Test
    void shouldGetEventsForDay() {
        // GIVEN
        Date expectedDate = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023, Month.APRIL, 30), LocalTime.of(12, 1)));
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Date-based test event", expectedDate, 2000L));
        List<EventImpl> expectedEvents = List.of(expectedEvent);

        // WHEN
        List<EventImpl> actualEvents = bookingFacade.getEventsForDay(expectedDate, 10, 1);

        // THEN
        Assertions.assertEquals(expectedEvents, actualEvents, "Events for day don't match with expected ones");

    }

    @Test
    void shouldGetEventsByTitle() {
        // GIVEN
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Lorem ipsum", new Date(), 2000L));
        List<EventImpl> expectedEvents = List.of(expectedEvent);

        // WHEN
        List<EventImpl> actualEvents = bookingFacade.getEventsByTitle("Lore", 10, 1);

        // THEN
        Assertions.assertEquals(expectedEvents, actualEvents, "Events by title don't match with expected ones");
    }

    @Test
    void shouldCreateEvent() {
        // GIVEN
        EventImpl expectedEvent = new EventImpl( "vitae, eleifend", new Date(), 100L);

        // WHEN
        EventImpl actualCreatedEvent = bookingFacade.createEvent(expectedEvent);

        // THEN
        EventImpl actualEventById = eventDao.findById(expectedEvent.getId()).orElse(null);
        Assertions.assertEquals(expectedEvent, actualCreatedEvent, "Created and expected events don't match");
        Assertions.assertEquals(expectedEvent, actualEventById, "Created event was not found by getById method");
    }

    @Test
    void shouldUpdateEvent() {
        // GIVEN
        EventImpl event = eventDao.save(new EventImpl( "Test event", new Date(), 1200L));
        EventImpl expectedEvent = new EventImpl(event.getId(), "Updated test event", new Date(),1900L);

        // WHEN
        EventImpl actualEvent = bookingFacade.updateEvent(expectedEvent);

        // THEN
        EventImpl actualEventById = eventDao.findById(event.getId()).orElse(null);
        Assertions.assertEquals(expectedEvent, actualEvent, "Updated and expected event don't match");
        Assertions.assertEquals(expectedEvent, actualEventById, "Updated and expected event don't match");
        Assertions.assertEquals(expectedEvent.getPrice(), actualEvent.getPrice());
        Assertions.assertEquals(expectedEvent.getTitle(), actualEvent.getTitle());
        Assertions.assertEquals(expectedEvent.getDate(), actualEvent.getDate());
    }

    @Test
    void shouldDeleteEvent() {
        // GIVEN
        EventImpl event = eventDao.save(new EventImpl( "Test event", new Date(), 1200L));

        // WHEN
        boolean isDeleted = bookingFacade.deleteEvent(event.getId());

        // THEN
        boolean isPresent = eventDao.existsById(event.getId());
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(isPresent);
    }

    @Test
    void shouldGetUserById() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("Test User", "mail@com", new UserAccount(1000L)));

        // WHEN
        UserImpl actualUser = bookingFacade.getUserById(expectedUser.getId());

        // THEN
        Assertions.assertEquals(expectedUser, actualUser, "User by id doesn't match with expected one");
    }

    @Test
    void shouldGetUserByEmail() {
        // GIVEN
        String email = "testmail@com";
        UserImpl expectedUser = userDao.save(new UserImpl("Email test user", email, new UserAccount(100L)));

        // WHEN
        UserImpl actualUser = bookingFacade.getUserByEmail(email);

        // THEN
        Assertions.assertEquals(expectedUser, actualUser, "User by email doesn't match with expected one");
    }

    @Test
    void shouldGetUsersByName() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("Maecenas nec", "testmail@com", new UserAccount(100L)));
        List<UserImpl> expectedUsers = List.of(expectedUser);

        // WHEN
        List<UserImpl> actualUsers = bookingFacade.getUsersByName("nec", 10, 1);

        // THEN
        Assertions.assertEquals(expectedUsers, actualUsers, "Users by name doesn't match with expected ones");
    }

    @Test
    void shouldCreateUser() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("cursus nunc", "testmail@com", new UserAccount(100L)));

        // WHEN
        UserImpl actualCreatedUser = bookingFacade.createUser(expectedUser);

        // THEN
        UserImpl actualByIdUser = userDao.findById(expectedUser.getId()).orElse(null);
        Assertions.assertEquals(expectedUser, actualByIdUser, "Created and expected users don't match");
        Assertions.assertEquals(expectedUser, actualCreatedUser, "Created and expected users don't match");
    }

    @Test
    void shouldUpdateUser() {
        // GIVEN
        UserImpl user = userDao.save(new UserImpl("Curabitur ullamcorper", "testmail@com", new UserAccount(100L)));
        UserImpl expectedUser = new UserImpl(user.getId(), "Updated User Name", "updated@mail.com", new UserAccount(0L));

        // WHEN
        UserImpl actualUser = bookingFacade.updateUser(expectedUser);

        // THEN
        UserImpl actualByIdUser = userDao.findById(user.getId()).orElse(null);
        Assertions.assertEquals(expectedUser, actualByIdUser, "Created and expected users don't match");
        Assertions.assertEquals(expectedUser, actualUser, "Created and expected users don't match");
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getUserAccount().getBalance(), actualUser.getUserAccount().getBalance());
    }

    @Test
    void shouldDeleteUser() {
        // GIVEN
        UserImpl user = userDao.save(new UserImpl("Curabitur ullamcorper", "testmail@com", new UserAccount(100L)));

        // WHEN
        boolean isDeleted = bookingFacade.deleteUser(user.getId());

        // THEN
        boolean isPresent = userDao.existsById(user.getId());
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(isPresent);
    }

    @Test
    void shouldBookTicket() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("Curabitur ullamcorper", "testmail@com", new UserAccount(1300L)));
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Test expectedEvent", new Date(), 1200L));
        long expectedTotalBalance = expectedUser.getUserAccount().getBalance() - expectedEvent.getPrice();
        Ticket.Category expectedCategory = Ticket.Category.STANDARD;
        int expectedPlace = 1;

        // WHEN
        TicketImpl actualTicket = bookingFacade.bookTicket(expectedUser.getId(), expectedEvent.getId(), expectedPlace, expectedCategory);

        // THEN
        Assertions.assertEquals(expectedPlace, actualTicket.getPlace());
        Assertions.assertEquals(expectedCategory, actualTicket.getCategory());
        UserImpl actualUser = userDao.findById(expectedUser.getId()).orElse(null);
        Assertions.assertEquals(expectedTotalBalance, actualUser.getUserAccount().getBalance());
        Assertions.assertEquals(expectedEvent.getId(), actualTicket.getEvent().getId());
        Assertions.assertEquals(expectedUser.getId(), actualTicket.getUser().getId());
    }

    @Test
    void shouldGetBookedTicketsByUser() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("augue velit cursus", "testmail@com", new UserAccount(1300L)));
        Date expectedDate1 = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023, Month.MARCH, 15), LocalTime.of(12, 0)));
        EventImpl expectedEvent1 = eventDao.save(new EventImpl( "augue velit cursus", expectedDate1, 1200L));
        TicketImpl expectedTicket1 = ticketDao.save(new TicketImpl(Ticket.Category.BAR, 34, expectedEvent1, expectedUser));
        Date expectedDate2 = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023, Month.MAY, 1), LocalTime.of(8, 0)));
        EventImpl expectedEvent2 = eventDao.save(new EventImpl( "blandit vel", expectedDate2, 1200L));
        TicketImpl expectedTicket2 = ticketDao.save(new TicketImpl(Ticket.Category.STANDARD, 99, expectedEvent2, expectedUser));
        List<TicketImpl> expectedTickets = List.of(expectedTicket2, expectedTicket1);

        // WHEN
        List<TicketImpl> actualBookedTickets = bookingFacade.getBookedTickets(expectedUser, 10, 1);

        // THEN
        Assertions.assertEquals(expectedTickets, actualBookedTickets);
    }

    @Test
    void shouldGetBookedTicketsByEvent() {
        // GIVEN
        EventImpl expectedEvent = eventDao.save(new EventImpl( "sapien ut libero", new Date(), 100L));
        UserImpl expectedUser1 = userDao.save(new UserImpl("sapien ut libero user1", "yemail@com", new UserAccount(1300L)));
        UserImpl expectedUser2 = userDao.save(new UserImpl("sapien ut libero user2", "aemail@com", new UserAccount(1340L)));
        TicketImpl expectedTicket1 = ticketDao.save(new TicketImpl(Ticket.Category.PREMIUM, 90, expectedEvent, expectedUser1));
        TicketImpl expectedTicket2 = ticketDao.save(new TicketImpl(Ticket.Category.BAR, 189, expectedEvent, expectedUser2));
        List<TicketImpl> expectedTickets = List.of(expectedTicket2, expectedTicket1);

        // WHEN
        List<TicketImpl> actualBookedTickets = bookingFacade.getBookedTickets(expectedEvent, 10, 1);

        // THEN
        Assertions.assertEquals(expectedTickets, actualBookedTickets);
    }

    @Test
    void shouldCancelTicket() {
        // GIVEN
        UserImpl expectedUser = userDao.save(new UserImpl("Dosec vitae", "testmail@com", new UserAccount(1300L)));
        EventImpl expectedEvent = eventDao.save(new EventImpl( "Dosec vitae", new Date(), 1200L));
        TicketImpl ticket = ticketDao.save(new TicketImpl(Ticket.Category.PREMIUM, 32, expectedEvent, expectedUser));

        // WHEN
        boolean isDeleted = bookingFacade.cancelTicket(ticket.getId());

        // THEN
        boolean isPresent = ticketDao.existsById(ticket.getId());
        System.out.println("isDeleted" + isDeleted);
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(isPresent);
    }

    @Test
    void shouldRefillUserAccount() {
        UserImpl expectedUser = userDao.save(new UserImpl("Dosec vitae", "testmail@com", new UserAccount(1300L)));
        long expectedRefilledBalance = 2300L;

        UserImpl actualUser = bookingFacade.refillUserAccount(expectedUser, new UserAccount(1000L));

        Assertions.assertEquals(actualUser, expectedUser);
        Assertions.assertEquals(expectedRefilledBalance, actualUser.getUserAccount().getBalance());
    }
}
