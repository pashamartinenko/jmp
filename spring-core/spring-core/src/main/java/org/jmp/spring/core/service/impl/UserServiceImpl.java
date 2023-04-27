package org.jmp.spring.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.UserDao;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.service.UserService;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserDao userDao;

    public User getUserById(long userId) {
        log.info("get user by id {}", userId);
        return userDao.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        log.info("get user by email {}", email);
        return userDao.getUserByEmail(email);
    }
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("get user by name {}", name);
        return userDao.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        log.info("create user {}", user);
        return userDao.createUser(user);
    }

    public User updateUser(User user) {
        log.info("update user {}", user);
        return userDao.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        log.info("delete user by id {}", userId);
        return userDao.deleteUser(userId);
    }
}
