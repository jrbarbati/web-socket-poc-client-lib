package com.personal.scheduled;

import com.personal.config.WebSocketContext;
import com.personal.publish.message.Hello;
import com.personal.publish.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "web-socket-poc.scheduled.heartbeat.enabled", matchIfMissing = true)
public class Heartbeat
{
    private static final Logger log = LogManager.getLogger(Heartbeat.class);

    private final HelloService helloService;

    @Autowired
    public Heartbeat(HelloService helloService)
    {
        this.helloService = helloService;
    }

    @Scheduled(cron = "${web-socket-poc.scheduled.heartbeat.cron}")
    public void heartbeat()
    {
        helloService.publish(
                Hello.builder()
                        .name(WebSocketContext.getAppId().toString())
                        .build()
        );
        log.info("Heartbeat published.");
    }
}
