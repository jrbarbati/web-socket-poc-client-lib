package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.message.PublishableMessage;

public abstract class AsyncPublisher<T extends PublishableMessage>
{
    private final String uri;
    private final WebSocketSessionManager sessionManager;

    public AsyncPublisher(String uri, WebSocketSessionManager sessionManager)
    {
        this.uri = uri;
        this.sessionManager = sessionManager;
    }

    public void publish(T message)
    {
        getSessionManager().getStompSession().send(uri, message);
    }

    public WebSocketSessionManager getSessionManager()
    {
        return sessionManager;
    }
}
