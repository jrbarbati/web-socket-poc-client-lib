package com.personal.batch.hatcher.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If no {@code orgId} can be specified on the annotation, then you can use {@code @OrgIdParam} on the method this annotates to
 * pass the {@code orgId} into the request for the distributed batch. {@code orgId} not required for the distributed batch.
 * Examples:
 * <pre class="code">
 * &#064;DistributedBatch(name = "genericScheduledProcess", orgId = 2)
 * public void runForOrgId()
 * {
 *     ...
 * }
 *
 *
 * &#064;DistributedBatch(name = "genericScheduledProcess")
 * protected void runForOrgId(&#064;OrgIdParam int orgId, ...)
 * {
 *     ...
 * }
 *
 * &#064;DistributedBatch(name = "genericScheduledProcess")
 * protected void runForOrgId(...)
 * {
 *     // No orgId so the distributed batch will request to run without an orgId specified.
 *     ...
 * }
 * </pre>
 * <p>
 * It would seem the {@code @DistributedBatch} implementation will require abstracting the actual running of the batch job to another
 * class/component. This is due to the fact that Spring will not run the Aspect code for the {@code @DistributedBatch} annotation
 * if it is called within the same class.
 * For example:
 * <pre class="code">
 * &#064;Component
 * public class ScheduledComponent
 * {
 *     &#064;Autowired
 *     AbstractedExecutor abstractedExecutor;
 *
 *     &#064;Scheduled(cron="...")
 *     public void do()
 *     {
 *         abstractedExecutor.doIt(...);
 *     }
 * }
 *
 * &#064;Component
 * public class AbstractedExecutor
 * {
 *     &#064;DistributedBatch(name = "doIt")
 *     public void doIt(&#064;OrgIdParam int orgId, ...)
 *     {
 *         ...
 *     }
 *
 *     // OR
 *
 *     &#064;DistributedBatch(name = "doIt")
 *     public void doIt(...)
 *     {
 *         ...
 *     }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedBatch
{
    String name() default "";
    int orgId() default 0;
}
