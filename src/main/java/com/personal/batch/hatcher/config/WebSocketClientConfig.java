package com.personal.batch.hatcher.config;

import com.personal.batch.hatcher.handler.WebSocketSessionHandler;
import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.topic.definition.SubscriptionDefinition;
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
    @Bean
    public WebSocketSessionManager sessionManager()
    {
        return new WebSocketSessionManager();
    }

    @Bean
    public WebSocketSessionHandler webSocketSessionHandler(@Value("${web-socket-poc.url}") String webSocketUrl,
                                                           WebSocketStompClient webSocketStompClient,
                                                           WebSocketSessionManager sessionManager,
                                                           List<SubscriptionDefinition> subscriptions)
    {
        return new WebSocketSessionHandler(webSocketUrl, webSocketStompClient, sessionManager, subscriptions);
    }

    @Bean
    public WebSocketStompClient webSocketStompClient()
    {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        return stompClient;
    }
}
