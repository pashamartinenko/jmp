package org.jmp.spring.mvc.dao;

import org.jmp.spring.mvc.model.impl.EventImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventDao extends CrudRepository<EventImpl, Long>
{
    List<EventImpl> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<EventImpl> findAllByDate(Date day, Pageable pageable);

    EventImpl findByIdAndTitleAndDate(Long id, String title, Date date);
}
