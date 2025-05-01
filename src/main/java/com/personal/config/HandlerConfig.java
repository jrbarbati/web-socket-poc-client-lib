package com.personal.config;

import com.personal.topic.handler.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfig
{
    @Bean
    public GreetingHandler greetingHandler()
    {
        return new GreetingHandler();
    }
}
