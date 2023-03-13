package com.jmp.nosql.couchbase.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
@RequiredArgsConstructor
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration
{
    private final CouchbaseProperties couchbaseProperties;

    @Override
    public String getConnectionString()
    {
        return couchbaseProperties.getConnectionString();
    }

    @Override
    public String getUserName()
    {
        return couchbaseProperties.getUserName();
    }

    @Override
    public String getPassword()
    {
        return couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName()
    {
        return couchbaseProperties.getBucketName();
    }
}
