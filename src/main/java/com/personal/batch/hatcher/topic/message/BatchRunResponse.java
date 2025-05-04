package com.personal.batch.hatcher.topic.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class BatchRunResponse extends TopicMessage
{
    private UUID instanceId;
    private String name;
    private Integer orgId;

    @JsonProperty("shouldRun")
    private Boolean shouldRun;

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

    public Integer getOrgId()
    {
        return orgId;
    }

    public void setOrgId(Integer orgId)
    {
        this.orgId = orgId;
    }

    @JsonProperty("shouldRun")
    public Boolean shouldRun()
    {
        if (shouldRun == null)
            shouldRun = false;

        return shouldRun;
    }

    @JsonProperty("shouldRun")
    public void setShouldRun(Boolean shouldRun)
    {
        this.shouldRun = shouldRun;
    }
}
