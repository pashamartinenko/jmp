package com.jmp.nosql.couchbase.service.impl;

import com.jmp.nosql.couchbase.model.User;
import com.jmp.nosql.couchbase.repository.UserFTSRepository;
import com.jmp.nosql.couchbase.service.UserFTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFTSServiceImpl implements UserFTSService
{
    private final UserFTSRepository userFTSRepository;

    public List<User> searchUsers(String query)
    {
        return userFTSRepository.searchUsers(query);
    }
}
