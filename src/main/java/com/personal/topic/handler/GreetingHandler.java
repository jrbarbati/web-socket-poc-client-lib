package com.personal.topic.handler;

import com.personal.topic.message.Greeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class GreetingHandler implements TopicHandler<Greeting>
{
    private static final Logger log = LogManager.getLogger(GreetingHandler.class);

    @Override
    public void process(Greeting greeting)
    {
        log.info("Greeting received: {}", greeting.getContent());
    }

    @Override
    public Type getPayloadType()
    {
        return Greeting.class;
    }
}
