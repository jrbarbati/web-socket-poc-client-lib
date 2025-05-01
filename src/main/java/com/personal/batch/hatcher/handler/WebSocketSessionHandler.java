package com.personal.batch.hatcher.handler;

import com.personal.batch.hatcher.topic.definition.SubscriptionDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.List;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter
{
    private static final Logger log = LogManager.getLogger(WebSocketSessionHandler.class);

    private final WebSocketSessionManager sessionManager;
    private final List<SubscriptionDefinition> subscriptions;

    public WebSocketSessionHandler(
            WebSocketSessionManager sessionManager,
            List<SubscriptionDefinition> subscriptions
    )
    {
        this.sessionManager = sessionManager;
        this.subscriptions = subscriptions;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders)
    {
        log.info("Connected.");

        subscriptions.forEach(sub -> {
            session.subscribe(sub.topic(), sub.handler());
            log.info("Subscribed to {}", sub.topic());
        });

        sessionManager.setStompSession(session);
    }
}
