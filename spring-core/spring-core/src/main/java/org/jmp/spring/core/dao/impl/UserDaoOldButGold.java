package org.jmp.spring.core.dao.impl;

import lombok.Setter;
import org.jmp.spring.core.model.User;
import org.jmp.spring.core.storage.Storage;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Setter
public class UserDaoOldButGold
{
    public static final String USER_PREFIX = "user:";

    private Storage storage;

    public User getUserById(long userId) {
        return (User) storage.getStorageMap().get(USER_PREFIX + userId);
    }

    public User getUserByEmail(String email) {
        return storage.getStorageMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(USER_PREFIX))
                .map(entry -> (User) entry.getValue())
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findAny()
                .orElse(null);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return storage.getStorageMap().entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(USER_PREFIX))
                .map(entry -> (User) entry.getValue())
                .filter(user -> user.getName().contains(name))
                .skip(pageSize * pageNum)
                .limit(pageSize)
                .toList();
    }

    public User createUser(User user) {
        user.setId(new Random().nextLong());
        storage.getStorageMap().put(USER_PREFIX + user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        User anUser = getUserById(user.getId());
        anUser.setEmail(user.getEmail());
        anUser.setName(user.getName());
        storage.getStorageMap().put(USER_PREFIX + user.getId(), anUser);
        return anUser;
    }

    public boolean deleteUser(long userId) {
        String key = USER_PREFIX + userId;
        Map<String, Object> st = storage.getStorageMap();
        if(st.containsKey(key)) {
            st.remove(key);
            return true;
        }
        return false;
    }
}
