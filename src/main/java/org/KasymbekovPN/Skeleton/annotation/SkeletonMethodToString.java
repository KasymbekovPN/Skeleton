package org.KasymbekovPN.Skeleton.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//< SKEL-31
@SkeletonMethod
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkeletonMethodToString{
    String name() default "toString";
    SkeletonArguments[] arguments() default {};
}
