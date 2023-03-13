package com.jmp.nosql.couchbase.service.impl;

import com.jmp.nosql.couchbase.model.Sport;
import com.jmp.nosql.couchbase.model.User;
import com.jmp.nosql.couchbase.repository.UserRepository;
import com.jmp.nosql.couchbase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public User findById(String id)
    {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public User updateSport(String userId, Sport sport) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setSport(sport);
            userRepository.save(user);
        }
        return user;
    }

    public List<User> getUsersBySportName(String sportName)
    {
        return userRepository.findUsersBySportName(sportName);
    }
}
