package org.jmp.spring.crud.dao;

import org.jmp.spring.crud.model.impl.EventImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends CrudRepository<EventImpl, Long>
{
}
