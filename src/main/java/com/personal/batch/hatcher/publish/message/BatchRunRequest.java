package com.personal.batch.hatcher.publish.message;

import java.util.UUID;

public class BatchRunRequest extends PublishableMessage
{
    private UUID instanceId;
    private String name;
    private Integer orgId;

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

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final BatchRunRequest batchRunRequest = new BatchRunRequest();

        public Builder instanceId(UUID instanceId)
        {
            batchRunRequest.setInstanceId(instanceId);
            return this;
        }

        public Builder name(String name)
        {
            batchRunRequest.setName(name);
            return this;
        }

        public Builder orgId(Integer orgId)
        {
            batchRunRequest.setOrgId(orgId);
            return this;
        }

        public Builder correlationId(UUID correlationId)
        {
            batchRunRequest.setCorrelationId(correlationId);
            return this;
        }

        public BatchRunRequest build()
        {
            return batchRunRequest;
        }
    }
}
