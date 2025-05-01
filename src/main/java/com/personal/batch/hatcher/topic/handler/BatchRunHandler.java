package com.personal.batch.hatcher.topic.handler;

import com.personal.batch.hatcher.config.WebSocketContext;
import com.personal.batch.hatcher.publish.service.BatchRunPublisher;
import com.personal.batch.hatcher.topic.message.BatchRunResponse;

import java.lang.reflect.Type;
import java.util.Objects;

public class BatchRunHandler implements TopicHandler<BatchRunResponse>
{
    private final BatchRunPublisher batchRunPublisher;

    public BatchRunHandler(BatchRunPublisher batchRunPublisher)
    {
        this.batchRunPublisher = batchRunPublisher;
    }

    @Override
    public void process(BatchRunResponse message)
    {
        if (!isForMyInstance(message))
            return;

        batchRunPublisher.complete(message);
    }

    protected boolean isForMyInstance(BatchRunResponse message)
    {
        return Objects.equals(message.getInstanceId(), WebSocketContext.getInstanceId());
    }

    @Override
    public Type getPayloadType()
    {
        return BatchRunResponse.class;
    }
}
