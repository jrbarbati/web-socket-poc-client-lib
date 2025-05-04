package com.personal.batch.hatcher.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in conjunction with {@code @DistributedBatch}.
 *
 * {@code @OrgIdParam} should mark the param in the method that is the {@code orgId}, if one exists.
 * The param MUST be of type int or Integer.
 *
 * See {@link com.personal.batch.hatcher.annotation.DistributedBatch @DistributedBatch}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrgIdParam
{
}
