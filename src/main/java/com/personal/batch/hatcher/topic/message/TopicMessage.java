package com.personal.batch.hatcher.topic.message;

import java.util.UUID;

public class TopicMessage
{
    private UUID correlationId;

    public UUID getCorrelationId()
    {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId)
    {
        this.correlationId = correlationId;
    }
}
