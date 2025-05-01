package com.personal.publish.service;

import com.personal.handler.WebSocketSessionManager;
import com.personal.publish.message.PublishableMessage;

public abstract class PublishService<T extends PublishableMessage>
{
    private final WebSocketSessionManager sessionManager;

    public PublishService(WebSocketSessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    public abstract void publish(T message);

    public WebSocketSessionManager getSessionManager()
    {
        return sessionManager;
    }
}
