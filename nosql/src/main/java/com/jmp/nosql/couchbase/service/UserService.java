package com.jmp.nosql.couchbase.service;

import com.jmp.nosql.couchbase.model.Sport;
import com.jmp.nosql.couchbase.model.User;

import java.util.List;

public interface UserService
{
    User findById(String id);

    List<User> findByEmail(String email);

    User save(User user);

    User updateSport(String userId, Sport sport);

    List<User> getUsersBySportName(String sportName);
}
