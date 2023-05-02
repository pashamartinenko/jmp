package org.jmp.spring.core.service.impl;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.dao.UserDao;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.model.impl.UserImpl;
import org.jmp.spring.core.service.UserService;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserDao userDao;

    public UserImpl getUserById(long userId) {
        log.info("get user by id {}", userId);
        return userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException(format("User with id=%d does not exist", userId)));
    }

    public User getUserByEmail(String email) {
        log.info("get user by email {}", email);
        return userDao.findByEmail(email);
    }

    public List<? extends User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("get user by name {}", name);
        return userDao.findAllByNameContainingIgnoreCase(name, PageRequest.of(pageNum - 1, pageSize));
    }

    public User createUser(UserImpl user) {
        log.info("create user {}", user);
        return userDao.save(user);
    }

    public User updateUser(UserImpl user) {
        User existingUser = getUserById(user.getId());
        log.info("update user {}", existingUser);
        return userDao.save(user);
    }

    public boolean deleteUser(long userId) {
        log.info("delete user by id {}", userId);
        boolean isFound = userDao.existsById(userId);
        if(isFound) {
            userDao.deleteById(userId);
        }
        return isFound;
    }

    public UserImpl findByIdAndNameAndEmail(Long id, String name, String email) {
        log.info("find user by id={}, name={} and email={}", id, name, email);
        return userDao.findByIdAndNameAndEmail(id, name, email);
    }
}
