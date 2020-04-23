package org.KasymbekovPN.Skeleton.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Skeleton {
    int includeByModifiers() default -1;
    int excludeByModifiers() default -1;
    String[] includeByName() default {};
    String[] excludeByName() default {};
}
