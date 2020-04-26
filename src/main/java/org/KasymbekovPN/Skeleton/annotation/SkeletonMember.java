package org.KasymbekovPN.Skeleton.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SkeletonMember {
    String[] memberParent() default {"member"};
}
