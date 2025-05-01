package com.personal.publish.service;

import com.personal.handler.WebSocketSessionManager;
import com.personal.publish.message.Hello;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloService extends PublishService<Hello>
{
    private static final Logger log = LogManager.getLogger(HelloService.class);

    private final String helloPublishUri;

    @Autowired
    public HelloService(
            @Value("${web-socket-poc.publish.hello}") String helloPublishUri,
            WebSocketSessionManager sessionManager
    )
    {
        super(sessionManager);
        this.helloPublishUri = helloPublishUri;
    }

    @Override
    public void publish(Hello message)
    {
        getSessionManager().getStompSession().send(helloPublishUri, message);
    }
}
