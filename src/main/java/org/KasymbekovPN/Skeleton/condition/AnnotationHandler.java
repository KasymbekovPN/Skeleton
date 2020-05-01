package org.KasymbekovPN.Skeleton.condition;

import java.lang.annotation.Annotation;
import java.util.List;

public interface AnnotationHandler {
    default void check(String name) {}
    default void check(int modifiers) {}
    default void check(Annotation[] annotations) {}
    default Annotation getAnnotation() {return  null;}
    SkeletonCheckResult getCheckResult();
    void resetCheckResult();
    List<String> getPath();
}
