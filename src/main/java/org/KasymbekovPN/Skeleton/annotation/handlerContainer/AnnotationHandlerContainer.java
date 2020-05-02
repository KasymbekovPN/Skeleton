package org.KasymbekovPN.Skeleton.annotation.handlerContainer;

import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;

import java.util.Optional;
import java.util.Set;

public interface AnnotationHandlerContainer {
//    void setClassAnnotationHandler(AnnotationHandler classAH);
//    void setMemberAnnotationHandler(AnnotationHandler memberAH);
//    AnnotationHandler getClassAnnotationHandler();
    //<
    void setAnnotationHandler(Entity entity, AnnotationHandler annotationHandler);
    Optional<AnnotationHandler> getAnnotationHandler(Entity entity);
    void addMemberName(String memberName);
    Set<String> getMemberName();
}
