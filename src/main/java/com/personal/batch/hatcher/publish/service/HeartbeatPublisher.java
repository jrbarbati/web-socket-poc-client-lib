package com.personal.batch.hatcher.publish.service;

import com.personal.batch.hatcher.handler.WebSocketSessionManager;
import com.personal.batch.hatcher.publish.message.Heartbeat;

public class HeartbeatPublisher extends AsyncPublisher<Heartbeat>
{
    public HeartbeatPublisher(String helloPublishUri, WebSocketSessionManager sessionManager)
    {
        super(helloPublishUri, sessionManager);
    }
}
