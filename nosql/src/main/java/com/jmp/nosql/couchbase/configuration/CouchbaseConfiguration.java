package com.jmp.nosql.couchbase.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration
{
    @Override
    public String getConnectionString()
    {
        return "localhost";
    }

    @Override
    public String getUserName()
    {
        return "admin";
    }

    @Override
    public String getPassword()
    {
        return "password";
    }

    @Override
    public String getBucketName()
    {
        return "users";
    }
}
