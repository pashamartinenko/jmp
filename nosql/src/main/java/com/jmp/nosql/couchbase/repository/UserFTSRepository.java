package com.jmp.nosql.couchbase.repository;

import com.jmp.nosql.couchbase.model.User;

import java.util.List;

public interface UserFTSRepository
{
    List<User> searchUsers(String query);
}
