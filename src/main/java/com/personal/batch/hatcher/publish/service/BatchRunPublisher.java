package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.message.BatchRunRequest;
import com.personal.batch.hatcher.topic.message.BatchRunResponse;

public class BatchRunPublisher extends AwaitingPublisher<BatchRunRequest, BatchRunResponse>
{
    public BatchRunPublisher(String batchRunRequestUri, WebSocketSessionManager sessionManager)
    {
        super(batchRunRequestUri, sessionManager);
    }
}
