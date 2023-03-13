package com.jmp.nosql.couchbase.service;

import com.jmp.nosql.couchbase.model.User;

import java.util.List;

public interface UserFTSService
{
    List<User> searchUsers(String query);
}
