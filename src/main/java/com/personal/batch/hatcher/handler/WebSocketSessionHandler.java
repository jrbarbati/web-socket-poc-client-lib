package com.personal.batch.hatcher.handler;

import com.personal.batch.hatcher.topic.definition.SubscriptionDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter
{
    private static final Logger log = LogManager.getLogger(WebSocketSessionHandler.class);
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final AtomicInteger retryAttempt = new AtomicInteger(0);
    private static final AtomicBoolean reconnecting = new AtomicBoolean(false);
    private static final Integer initialDelaySeconds = 5;
    private static final Integer maxDelaySeconds = 60;

    private final String webSocketUrl;
    private final WebSocketStompClient stompClient;
    private final WebSocketSessionManager sessionManager;
    private final List<SubscriptionDefinition> subscriptions;

    public WebSocketSessionHandler(
            String webSocketUrl,
            WebSocketStompClient stompClient,
            WebSocketSessionManager sessionManager,
            List<SubscriptionDefinition> subscriptions
    )
    {
        this.webSocketUrl = webSocketUrl;
        this.stompClient = stompClient;
        this.sessionManager = sessionManager;
        this.subscriptions = subscriptions;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders)
    {
        log.info("Connected.");

        retryAttempt.set(0);
        reconnecting.set(false);

        subscriptions.forEach(sub -> {
            session.subscribe(sub.topic(), sub.handler());
            log.info("Subscribed to {}", sub.topic());
        });

        sessionManager.setStompSession(session);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception)
    {
        try
        {
            log.error("Transport error. {} - {}", exception.getClass().getSimpleName(), exception.getMessage());

            session.disconnect();
            sessionManager.getStompSession().disconnect();
            sessionManager.setStompSession(null);
        } catch (Exception e)
        {
            log.error("Caught {} while trying to disconnect from the web socket session. {}", e.getClass().getSimpleName(), e.getMessage());
        } finally
        {
            attemptReconnect();
        }
    }

    protected void attemptReconnect()
    {
        if (reconnecting.getAndSet(true))
            return;

        scheduler.execute(() -> {
            while (sessionManager.getStompSession() == null || !sessionManager.getStompSession().isConnected())
            {
                int attempt = retryAttempt.incrementAndGet();
                int delay = Math.min(initialDelaySeconds * (1 << (attempt - 1)), maxDelaySeconds);
                log.info("Reconnect attempt {}. Waiting {} seconds before retrying...", attempt, delay);

                try
                {
                    TimeUnit.SECONDS.sleep(delay);
                    stompClient.connectAsync(webSocketUrl, this);
                }
                catch (Exception e)
                {
                    log.warn("Reconnect attempt {} failed: {}", attempt, e.getMessage());
                }
            }

            retryAttempt.set(0);
            reconnecting.set(false);
        });
    }
}
