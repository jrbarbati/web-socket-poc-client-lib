package com.personal.config;

import com.personal.handler.WebSocketSessionHandler;
import com.personal.handler.WebSocketSessionManager;
import com.personal.topic.definition.SubscriptionDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.List;

@Configuration
public class WebSocketClientConfig
{
    private final String webSocketUrl;

    @Autowired
    public WebSocketClientConfig(
            @Value("${web-socket-poc.url}") String webSocketUrl
    )
    {
        this.webSocketUrl = webSocketUrl;
    }

    @Bean
    public WebSocketSessionManager sessionManager()
    {
        return new WebSocketSessionManager();
    }

    @Bean
    public WebSocketSessionHandler webSocketSessionHandler(WebSocketSessionManager sessionManager,
                                                           List<SubscriptionDefinition> subscriptions)
    {
        return new WebSocketSessionHandler(sessionManager, subscriptions);
    }

    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketSessionHandler sessionHandler)
    {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connectAsync(webSocketUrl, sessionHandler);
        return stompClient;
    }
}
