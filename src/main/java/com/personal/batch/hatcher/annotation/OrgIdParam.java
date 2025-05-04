package com.personal.batch.hatcher.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @OrgIdParam} should mark the param in the method that is the orgId, if one exists.
 * The param MUST be of type int or Integer.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrgIdParam
{
}
