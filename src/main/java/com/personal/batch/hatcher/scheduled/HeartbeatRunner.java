package com.personal.batch.hatcher.scheduled;

import com.personal.batch.hatcher.config.WebSocketContext;
import com.personal.batch.hatcher.publish.message.Heartbeat;
import com.personal.batch.hatcher.publish.service.HeartbeatPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "web-socket-poc.scheduled.heartbeat.enabled", matchIfMissing = true)
public class HeartbeatRunner
{
    private static final Logger log = LogManager.getLogger(HeartbeatRunner.class);

    private final HeartbeatPublisher heartbeatPublisher;

    @Autowired
    public HeartbeatRunner(HeartbeatPublisher heartbeatPublisher)
    {
        this.heartbeatPublisher = heartbeatPublisher;
    }

    @Scheduled(cron = "${web-socket-poc.scheduled.heartbeat.cron}")
    public void heartbeat()
    {
        try
        {
            heartbeatPublisher.publish(
                    Heartbeat.builder()
                            .instanceId(WebSocketContext.getInstanceId())
                            .build()
            );
            log.info("Heartbeat published.");
        }
        catch (Exception e)
        {
            log.error("Heartbeat publish failed. {} - {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
