package com.personal.batch.hatcher.config;

import java.util.UUID;

public class WebSocketContext
{
    private static UUID instanceId;

    public static void setInstanceId(UUID id)
    {
        instanceId = id;
    }

    public static UUID getInstanceId()
    {
        return instanceId;
    }

    public static void clear()
    {
        instanceId = null;
    }
}
