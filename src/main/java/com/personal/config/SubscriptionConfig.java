package com.personal.config;

import com.personal.topic.definition.SubscriptionDefinition;
import com.personal.topic.handler.GreetingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionConfig
{
    private final String greetingsTopic;

    @Autowired
    public SubscriptionConfig(
            @Value("${web-socket-poc.topics.greetings}") String greetingsTopic
    )
    {
        this.greetingsTopic = greetingsTopic;
    }

    @Bean
    public SubscriptionDefinition greetingsSubscription(GreetingHandler greetingHandler)
    {
        return new SubscriptionDefinition(greetingsTopic, greetingHandler);
    }
}
