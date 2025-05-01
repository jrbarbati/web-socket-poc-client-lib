package com.personal.handler;

import org.springframework.messaging.simp.stomp.StompSession;

public class WebSocketSessionManager
{
    private StompSession stompSession;

    public StompSession getStompSession()
    {
        return stompSession;
    }

    public void setStompSession(StompSession stompSession)
    {
        this.stompSession = stompSession;
    }

    public boolean isConnected()
    {
        return getStompSession() != null && getStompSession().isConnected();
    }
}
