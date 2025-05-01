package com.personal.batch.hatcher.startup;

import com.personal.batch.hatcher.handler.WebSocketSessionHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class WebSocketClientStarter
{
    private final WebSocketStompClient stompClient;
    private final WebSocketSessionHandler sessionHandler;
    private final String webSocketUrl;

    @Autowired
    public WebSocketClientStarter(@Value("${web-socket-poc.url}") String webSocketUrl,
                                  WebSocketStompClient stompClient,
                                  WebSocketSessionHandler sessionHandler)
    {
        this.stompClient = stompClient;
        this.sessionHandler = sessionHandler;
        this.webSocketUrl = webSocketUrl;
    }

    @PostConstruct
    public void start()
    {
        stompClient.connectAsync(webSocketUrl, sessionHandler);
    }
}
