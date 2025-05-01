package com.personal.startup;

import com.personal.config.WebSocketContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OnStartup
{
    private static final Logger log = LogManager.getLogger(OnStartup.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady()
    {
        WebSocketContext.setAppId(UUID.randomUUID());
        log.info("Created UUID for WebSocket Context: {}", WebSocketContext.getAppId());
    }
}
