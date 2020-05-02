package org.KasymbekovPN.Skeleton.annotation.handler;

import org.KasymbekovPN.Skeleton.annotation.handlerContainer.AnnotationHandlerContainer;

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
    AnnotationHandlerContainer getContainer();
}
