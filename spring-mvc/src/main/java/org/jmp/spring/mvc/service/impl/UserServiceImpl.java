package org.jmp.spring.mvc.service.impl;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.mvc.dao.UserDao;
import org.jmp.spring.mvc.model.impl.UserAccount;
import org.jmp.spring.mvc.model.impl.UserImpl;
import org.jmp.spring.mvc.service.UserService;
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
                .orElseThrow(() -> new IllegalStateException(format("User with id=%d does not exist", userId)));
    }

    public UserImpl getUserByEmail(String email) {
        log.info("get user by email {}", email);
        return userDao.findByEmail(email);
    }

    public List<UserImpl> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("get user by name {}", name);
        return userDao.findAllByNameContainingIgnoreCase(name, PageRequest.of(pageNum - 1, pageSize));
    }

    public UserImpl createUser(UserImpl user) {
        log.info("create user {}", user);
        return userDao.save(user);
    }

    public UserImpl updateUser(UserImpl user) {
        UserImpl foundUser = getUserById(user.getId());
        log.info("update user {}", foundUser);
        String email = user.getEmail();
        if (email != null) {
            foundUser.setEmail(email);
        }
        String name = user.getName();
        if (name != null) {
            foundUser.setName(name);
        }
        UserAccount userAccount = user.getUserAccount();
        if (userAccount != null) {
            Long balance = userAccount.getBalance();
            if (balance != null) {
                foundUser.setUserAccount(userAccount);
            }
        }
        return userDao.save(foundUser);
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

    public UserImpl refillUserAccount(UserImpl user, UserAccount userAccount) {
        log.info("Refill user account by userAccount={} for user={}", userAccount, user);
        UserImpl existingUser = findByIdAndNameAndEmail(user.getId(), user.getName(), user.getEmail());
        if (existingUser == null)
        {
            throw new IllegalStateException(format("User with name=%s and email=%s does not exist", user.getName(), user.getEmail()));
        }

        UserAccount existingUserAccount = existingUser.getUserAccount();
        if (existingUserAccount != null) {
            Long balance = existingUserAccount.getBalance();
            if (balance != null) {
                userAccount.addBalance(balance);
            }
        }
        user.setUserAccount(userAccount);
        return userDao.save(user);
    }
}
