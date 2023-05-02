package org.jmp.spring.core.dao;

import org.jmp.spring.core.model.impl.UserImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<UserImpl, Long>
{
    UserImpl findByEmail(String email);

    List<UserImpl> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    UserImpl findByIdAndNameAndEmail(Long id, String name, String email);
/*    User getUserById(long userId);
    User getUserByEmail(String email);
    List<User> getUsersByName(String name, int pageSize, int pageNum);*/
/*    User updateUser(User user);
    boolean deleteUser(long userId);*/
}
