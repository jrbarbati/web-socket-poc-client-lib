package com.personal.batch.hatcher.annotation;

import com.personal.batch.hatcher.config.WebSocketContext;
import com.personal.batch.hatcher.publish.message.BatchRunRequest;
import com.personal.batch.hatcher.publish.service.BatchRunPublisher;
import com.personal.batch.hatcher.topic.message.BatchRunResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
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
            if (!determineIfIShouldRun(distributedBatch, joinPoint))
            {
                log.trace("Not allowed to execute batch job {} for org {}", distributedBatch.name(), distributedBatch.orgId());
                return null;
            }

            log.trace("Executing batch job {} for org {}", distributedBatch.name(), distributedBatch.orgId());

            return joinPoint.proceed();
        }
        catch (Throwable e)
        {
            log.error("Caught {} while executing {}. {}", e.getClass().getSimpleName(), joinPoint.getSignature(), e.getMessage());
            throw e;
        }
    }

    protected boolean determineIfIShouldRun(DistributedBatch distributedBatch, ProceedingJoinPoint joinPoint) throws Exception
    {
        Integer orgId = distributedBatch.orgId() != 0
                ? Integer.valueOf(distributedBatch.orgId())
                : extractOrgId(joinPoint);

        log.trace("Requesting to run distributed batch {} for org {}", distributedBatch.name(), orgId);

        CompletableFuture<BatchRunResponse> future = batchRunPublisher.publishAndAwaitResponse(
                BatchRunRequest.builder()
                        .instanceId(WebSocketContext.getInstanceId())
                        .name(distributedBatch.name())
                        .orgId(orgId)
                        .build()
        );

        return future.get(5, TimeUnit.SECONDS).shouldRun();
    }

    private Integer extractOrgId(ProceedingJoinPoint joinPoint)
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterAnnotations.length; i++)
            for (Annotation annotation : parameterAnnotations[i])
                if (annotation instanceof OrgIdParam)
                {
                    Object value = args[i];
                    if (value instanceof Integer)
                        return (Integer) value;

                    throw new IllegalArgumentException("@OrgIdParam must be on an Integer parameter");
                }

        return null;
    }
}
