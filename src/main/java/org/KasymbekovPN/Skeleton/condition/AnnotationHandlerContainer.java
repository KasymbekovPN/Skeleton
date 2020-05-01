package org.KasymbekovPN.Skeleton.condition;

public interface AnnotationHandlerContainer {
    void setClassAnnotationHandler(AnnotationHandler classAH);
    void setMemberAnnotationHandler(AnnotationHandler memberAH);
    AnnotationHandler getClassAnnotationHandler();
}
