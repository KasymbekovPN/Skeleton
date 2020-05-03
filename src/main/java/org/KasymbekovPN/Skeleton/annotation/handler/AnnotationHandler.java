package org.KasymbekovPN.Skeleton.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public interface AnnotationHandler {
//    default void check(String name) {}
//    default void check(int modifiers) {}
    default Optional<Annotation> check(Annotation[] annotations) { return Optional.empty();}
//    default void check(Collector collector) {}
    //<
    default Annotation getAnnotation() {return  null;}
    SkeletonCheckResult getCheckResult();
    void resetCheckResult();
    List<String> getPath();
    //<
//    AnnotationHandlerContainer getContainer();
}
