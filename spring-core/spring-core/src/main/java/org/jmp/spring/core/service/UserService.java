package org.jmp.spring.core.service;

import org.jmp.spring.core.model.impl.UserAccount;
import org.jmp.spring.core.model.impl.UserImpl;

import java.util.List;

public interface UserService
{
    UserImpl getUserById(long userId);
    UserImpl getUserByEmail(String email);
    List<UserImpl> getUsersByName(String name, int pageSize, int pageNum);
    UserImpl createUser(UserImpl user);
    UserImpl updateUser(UserImpl user);
    boolean deleteUser(long userId);
    UserImpl findByIdAndNameAndEmail(Long id, String name, String email);
    UserImpl refillUserAccount(UserImpl user, UserAccount userAccount);
}
