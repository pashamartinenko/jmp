package com.jmp.nosql.couchbase.controller;

import com.jmp.nosql.couchbase.model.User;
import com.jmp.nosql.couchbase.service.UserFTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchAPI
{
    private final UserFTSService userFTSService;

    @GetMapping("/search/user")
    public List<User> searchUsers(@RequestParam("q") String query) {
        return userFTSService.searchUsers(query);
    }
}
