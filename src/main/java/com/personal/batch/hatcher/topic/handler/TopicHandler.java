package com.personal.batch.hatcher.topic.handler;

import com.personal.batch.hatcher.topic.message.TopicMessage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public interface TopicHandler<T extends TopicMessage> extends StompFrameHandler
{
    void process(T message);
    Type getPayloadType();

    @Override
    default Type getPayloadType(StompHeaders headers)
    {
        return getPayloadType();
    }

    @Override
    default void handleFrame(StompHeaders headers, Object payload)
    {
        process((T) payload);
    }
}
