package com.personal.batch.hatcher.topic.definition;

import org.springframework.messaging.simp.stomp.StompFrameHandler;

public record SubscriptionDefinition(String topic, StompFrameHandler handler) {}
