package org.KasymbekovPN.Skeleton.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SkeletonConstructor {
    String[] members() default {};
    String[] constructorParent() default {"constructor"};
}
