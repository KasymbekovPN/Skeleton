package org.KasymbekovPN.Skeleton.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkeletonClass {
    int includeByModifiers() default -1;
    int excludeByModifiers() default -1;
    String[] includeByName() default {};
    String[] excludeByName() default {};
}
