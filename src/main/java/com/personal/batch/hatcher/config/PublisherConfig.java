package com.personal.batch.hatcher.config;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.service.BatchRunPublisher;
import com.personal.batch.hatcher.publish.service.HeartbeatPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig
{
    @Bean
    public HeartbeatPublisher heartbeatPublisher(
            @Value("${web-socket-poc.publish.heartbeat}") String heartbeatUri,
            WebSocketSessionManager webSocketSessionManager
    )
    {
        return new HeartbeatPublisher(heartbeatUri, webSocketSessionManager);
    }

    @Bean
    public BatchRunPublisher batchRunPublisher(
            @Value("${web-socket-poc.publish.batch-run}") String batchRunRequestUri,
            WebSocketSessionManager webSocketSessionManager
    )
    {
        return new BatchRunPublisher(batchRunRequestUri, webSocketSessionManager);
    }
}
