package org.jmp.spring.mvc.dao;

import org.jmp.spring.mvc.model.impl.UserImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<UserImpl, Long>
{
    UserImpl findByEmail(String email);

    List<UserImpl> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    UserImpl findByIdAndNameAndEmail(Long id, String name, String email);
}
