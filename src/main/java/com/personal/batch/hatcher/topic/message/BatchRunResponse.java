package com.personal.batch.hatcher.topic.message;

import java.util.UUID;

public class BatchRunResponse extends TopicMessage
{
    private UUID instanceId;
    private String name;
    private int orgId;
    private boolean shouldRun;

    public UUID getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(UUID instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOrgId()
    {
        return orgId;
    }

    public void setOrgId(int orgId)
    {
        this.orgId = orgId;
    }

    public boolean shouldRun()
    {
        return shouldRun;
    }

    public void setShouldRun(boolean shouldRun)
    {
        this.shouldRun = shouldRun;
    }
}
