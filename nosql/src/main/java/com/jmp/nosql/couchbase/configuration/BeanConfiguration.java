package com.jmp.nosql.couchbase.configuration;

import static com.couchbase.client.java.ClusterOptions.clusterOptions;

import com.couchbase.client.java.Cluster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration
{

    @Bean
    public Cluster cluster(CouchbaseConfiguration configuration) {
        return Cluster.connect(
                configuration.getConnectionString(),
                clusterOptions(configuration.getUserName(), configuration.getPassword()));
    }
}
