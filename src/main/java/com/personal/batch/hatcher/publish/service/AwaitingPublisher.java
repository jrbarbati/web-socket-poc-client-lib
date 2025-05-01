package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.message.PublishableMessage;
import com.personal.batch.hatcher.topic.message.TopicMessage;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;

public abstract class AwaitingPublisher<T extends PublishableMessage, U extends TopicMessage>
{
    private final String uri;
    private final WebSocketSessionManager sessionManager;
    private final ConcurrentHashMap<UUID, CompletableFuture<U>> responses;

    public AwaitingPublisher(String uri, WebSocketSessionManager sessionManager)
    {
        this.uri = uri;
        this.sessionManager = sessionManager;
        this.responses = new ConcurrentHashMap<>();
    }

    public CompletableFuture<U> publishAndAwaitResponse(T message)
    {
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
