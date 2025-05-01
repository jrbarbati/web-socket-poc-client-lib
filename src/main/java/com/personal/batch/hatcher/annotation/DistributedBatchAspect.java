package com.personal.batch.hatcher.annotation;

import com.personal.batch.hatcher.config.WebSocketContext;
import com.personal.batch.hatcher.publish.message.BatchRunRequest;
import com.personal.batch.hatcher.publish.service.BatchRunPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DistributedBatchAspect
{
    private static final Logger log = LogManager.getLogger(DistributedBatchAspect.class);

    private final BatchRunPublisher batchRunPublisher;

    @Autowired
    public DistributedBatchAspect(BatchRunPublisher batchRunPublisher)
    {
        this.batchRunPublisher = batchRunPublisher;
    }

    @Around("@annotation(distributedBatch)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, DistributedBatch distributedBatch) throws Throwable
    {
        try
        {
            if (!determineIfIShouldRun(distributedBatch))
            {
                log.info("Not allowed to execute batch job {} for org {}", distributedBatch.name(), distributedBatch.orgId());
                return null;
            }

            log.info("Executing batch job {} for org {}", distributedBatch.name(), distributedBatch.orgId());

            return joinPoint.proceed();
        }
        catch (Throwable e)
        {
            log.error("Caught {} while executing {}. {}", e.getClass().getSimpleName(), joinPoint.getSignature(), e.getMessage());
            throw e;
        }
    }

    protected boolean determineIfIShouldRun(DistributedBatch distributedBatch) throws Exception
    {
        log.info("Requesting to run distributed batch {} for org {}", distributedBatch.name(), distributedBatch.orgId());

        return batchRunPublisher.publishAndAwaitResponse(
                BatchRunRequest.builder()
                        .instanceId(WebSocketContext.getInstanceId())
                        .name(distributedBatch.name())
                        .orgId(distributedBatch.orgId())
                        .build()
        ).get(5, TimeUnit.SECONDS).shouldRun();
    }
}
