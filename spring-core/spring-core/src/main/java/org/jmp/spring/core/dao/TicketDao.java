package org.jmp.spring.core.dao;

import org.jmp.spring.core.model.impl.TicketImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TicketDao extends CrudRepository<TicketImpl, Long>
{
    @Query("SELECT t from TicketImpl t WHERE t.user.id = :userId")
    List<TicketImpl> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT t from TicketImpl t WHERE t.event.id = :eventId")
    List<TicketImpl> findByEventId(@Param("eventId") Long eventId, Pageable pageable);
}
