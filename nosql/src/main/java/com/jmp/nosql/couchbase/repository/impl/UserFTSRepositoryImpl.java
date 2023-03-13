package com.jmp.nosql.couchbase.repository.impl;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.search.SearchOptions;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import com.jmp.nosql.couchbase.model.User;
import com.jmp.nosql.couchbase.repository.UserFTSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserFTSRepositoryImpl implements UserFTSRepository
{
    private static final String FTS_INDEX_NAME = "users_fullName";
    private final Cluster cluster;

    public List<User> searchUsers(String query) {
        List<User> users = new ArrayList<>();

        try {
            final SearchResult result = cluster.searchQuery(FTS_INDEX_NAME, SearchQuery.queryString(query), SearchOptions.searchOptions().fields("*"));

            for (SearchRow row : result.rows()) {
                User e = row.fieldsAs(User.class);
                users.add(e);
            }
        } catch (CouchbaseException ex) {
            ex.printStackTrace();
        }

        return users;
    }
}
