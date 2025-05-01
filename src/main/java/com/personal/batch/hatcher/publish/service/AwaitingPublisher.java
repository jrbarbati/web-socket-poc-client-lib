package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.exception.PublishingException;
import com.personal.batch.hatcher.publish.message.PublishableMessage;
import com.personal.batch.hatcher.topic.message.TopicMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;

public abstract class AwaitingPublisher<T extends PublishableMessage, U extends TopicMessage>
{
    private static final Logger log = LogManager.getLogger(AwaitingPublisher.class);
    private final String uri;
    private final WebSocketSessionManager sessionManager;
    private final ConcurrentHashMap<UUID, CompletableFuture<U>> responses;

    public AwaitingPublisher(String uri, WebSocketSessionManager sessionManager)
    {
        this.uri = uri;
        this.sessionManager = sessionManager;
        this.responses = new ConcurrentHashMap<>();
    }

    public CompletableFuture<U> publishAndAwaitResponse(T message) throws PublishingException
    {
        if (!getSessionManager().isConnected())
        {
            String errorMessage = "Session is disconnected. Unable to publish message.";
            log.error(errorMessage);
            throw new PublishingException(errorMessage);
        }

        CompletableFuture<U> future = new CompletableFuture<>();

        if (message.getCorrelationId() == null)
            message.setCorrelationId(UUID.randomUUID());

        responses.put(message.getCorrelationId(), future);
        publish(message);

        return future;
    }

    protected void publish(T message)
    {
        getSessionManager().getStompSession().send(uri, message);
    }

    public void complete(U message)
    {
        CompletableFuture<U> future = responses.remove(message.getCorrelationId());

        if (future != null)
            future.complete(message);
    }

    public WebSocketSessionManager getSessionManager()
    {
        return sessionManager;
    }
}
