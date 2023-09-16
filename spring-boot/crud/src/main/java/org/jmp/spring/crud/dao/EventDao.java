package org.jmp.spring.crud.dao;

import org.jmp.spring.crud.model.impl.EventImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface EventDao extends CrudRepository<EventImpl, Long>

{
    List<EventImpl> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<EventImpl> findAllByDate(Date day, Pageable pageable);
}
