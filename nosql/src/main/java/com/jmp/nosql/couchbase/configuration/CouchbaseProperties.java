package com.jmp.nosql.couchbase.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CouchbaseProperties
{
    @Value("${spring.couchbase.connection-string}")
    private String connectionString;
    @Value("${spring.couchbase.bucket.user}")
    private String userName;
    @Value("${spring.couchbase.bucket.password}")
    private String password;
    @Value("${spring.couchbase.bucket.name}")
    private String bucketName;
}
