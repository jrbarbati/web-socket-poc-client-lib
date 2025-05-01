package com.personal.batch.hatcher.config;

import com.personal.batch.hatcher.topic.definition.SubscriptionDefinition;
import com.personal.batch.hatcher.topic.handler.BatchRunHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionConfig
{
    private final String batchRunTopic;

    @Autowired
    public SubscriptionConfig(
            @Value("${web-socket-poc.topics.batch-run}") String batchRunTopic
    )
    {
        this.batchRunTopic = batchRunTopic;
    }

    @Bean
    public SubscriptionDefinition batchRunSubscription(BatchRunHandler batchRunHandler)
    {
        return new SubscriptionDefinition(batchRunTopic, batchRunHandler);
    }
}
