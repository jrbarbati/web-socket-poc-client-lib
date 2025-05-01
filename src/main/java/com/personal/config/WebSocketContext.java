package com.personal.config;

import java.util.UUID;

public class WebSocketContext
{
    private static UUID appId;

    public static void setAppId(UUID id)
    {
        appId = id;
    }

    public static UUID getAppId()
    {
        return appId;
    }

    public static void clear()
    {
        appId = null;
    }
}
