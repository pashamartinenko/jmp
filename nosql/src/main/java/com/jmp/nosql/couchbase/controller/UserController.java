package com.jmp.nosql.couchbase.controller;

import com.jmp.nosql.couchbase.model.Sport;
import com.jmp.nosql.couchbase.model.User;
import com.jmp.nosql.couchbase.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController
{
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService)
    {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping("/user/email/{email}")
    public List<User> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/user")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/user/{id}/sport")
    public User updateSport(@PathVariable("id") String userId, @RequestBody Sport sport) {
        return userService.updateSport(userId, sport);
    }

    @GetMapping("/user/sport/{sportName}")
    public List<User> getUsersBySportName(@PathVariable String sportName) {
        return userService.getUsersBySportName(sportName);
    }
}
