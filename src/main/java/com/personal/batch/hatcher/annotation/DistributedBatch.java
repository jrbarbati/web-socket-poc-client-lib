package com.personal.batch.hatcher.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If no orgId can be specified on the annotation, then you can use {@code @OrgIdParam} on the method this annotates to
 * pass the orgId into the request for the distributed batch. orgId not required for the distributed batch.
 * Examples:
 *
 * @DistributedBatch(name = "genericScheduledProcess", orgId = 2)
 * public void runForOrgId()
 * {
 *     ...
 * }
 *
 *
 * @DistributedBatch(name = "genericScheduledProcess")
 * protected void runForOrgId(@OrgIdParam int orgId, ...)
 * {
 *     ...
 * }
 *
 * @DistributedBatch(name = "genericScheduledProcess")
 * protected void runForOrgId(...)
 * {
 *     // No orgId so the distributed batch will request to run without an orgId specified.
 *     ...
 * }
 *
 * It would seem the @DistributedBatch implementation will require abstracting the actual running of the batch job to another
 * class/component. This is due to the fact that Spring will not run the Aspect code for the @DistributedBatch annotation
 * if it is called within the same class.
 * For example:
 *
 * @Component
 * public class ScheduledComponent
 * {
 *     @Autowired
 *     AbstractedExecutor abstractedExecutor;
 *
 *     @Scheduled(cron="...")
 *     public void do()
 *     {
 *         abstractedExecutor.doIt(...);
 *     }
 * }
 *
 * @Component
 * public class AbstractedExecutor
 * {
 *     @DistributedBatch(name = "doIt")
 *     public void doIt(@OrgIdParam int orgId, ...)
 *     {
 *         ...
 *     }
 *
 *     // OR
 *     @DistributedBatch(name = "doIt")
 *     public void doIt(...)
 *     {
 *         ...
 *     }
 *
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedBatch
{
    String name() default "";
    int orgId() default 0;
}
