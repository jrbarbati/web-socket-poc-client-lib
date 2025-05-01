package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.exception.PublishingException;
import com.personal.batch.hatcher.publish.message.PublishableMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AsyncPublisher<T extends PublishableMessage>
{
    private static final Logger log = LogManager.getLogger(AsyncPublisher.class);
    private final String uri;
    private final WebSocketSessionManager sessionManager;

    public AsyncPublisher(String uri, WebSocketSessionManager sessionManager)
    {
        this.uri = uri;
        this.sessionManager = sessionManager;
    }

    public void publish(T message) throws PublishingException
    {
        if (!getSessionManager().isConnected())
        {
            String errorMessage = "Session is not connected. Unable to publish message.";
            log.error(errorMessage);
            throw new PublishingException(errorMessage);
        }

        getSessionManager().getStompSession().send(uri, message);
    }

    public WebSocketSessionManager getSessionManager()
    {
        return sessionManager;
    }
}
