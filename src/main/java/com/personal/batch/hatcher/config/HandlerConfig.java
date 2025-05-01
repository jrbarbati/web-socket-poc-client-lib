package com.personal.batch.hatcher.config;

import com.personal.batch.hatcher.publish.service.BatchRunPublisher;
import com.personal.batch.hatcher.topic.handler.BatchRunHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HandlerConfig
{
    @Bean
    public BatchRunHandler batchRunHandler(BatchRunPublisher batchRunPublisher)
    {
        return new BatchRunHandler(batchRunPublisher);
    }
}
