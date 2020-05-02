package org.KasymbekovPN.Skeleton.annotation.handler.constructor;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.annotation.handlerContainer.AnnotationHandlerContainer;
import org.KasymbekovPN.Skeleton.annotation.handlerContainer.Entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConstructorAH implements AnnotationHandler {

    private final AnnotationHandlerContainer annotationHandlerContainer;

    private SkeletonCheckResult annotationChecking = SkeletonCheckResult.NONE;
    private SkeletonCheckResult pathChecking = SkeletonCheckResult.NONE;
    private SkeletonConstructor annotation;
    private List<String> path;

    public ConstructorAH(AnnotationHandlerContainer annotationHandlerContainer) {
        this.annotationHandlerContainer = annotationHandlerContainer;
        this.annotationHandlerContainer.setAnnotationHandler(Entity.CONSTRUCTOR, this);
        resetCheckResult();
    }

    @Override
    public void check(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonConstructor.class)) {
                annotationChecking = SkeletonCheckResult.INCLUDE;
                this.annotation = (SkeletonConstructor) annotation;
            }
        }
    }

    @Override
    public Annotation getAnnotation() {
        return annotation;
    }

    @Override
    public SkeletonCheckResult getCheckResult() {
        extractPath();

        return annotationChecking.equals(SkeletonCheckResult.INCLUDE) && pathChecking.equals(SkeletonCheckResult.INCLUDE)
                ? SkeletonCheckResult.INCLUDE
                : SkeletonCheckResult.EXCLUDE;
    }

    @Override
    public void resetCheckResult() {
        annotationChecking = SkeletonCheckResult.NONE;
        pathChecking = SkeletonCheckResult.NONE;
    }

    @Override
    public List<String> getPath() {
        return path;
    }

    @Override
    public AnnotationHandlerContainer getContainer() {
        return annotationHandlerContainer;
    }

    private void extractPath(){
        if (annotationChecking.equals(SkeletonCheckResult.INCLUDE)){
            if (annotation.constructorParent().length != 0){
                path = Arrays.asList(annotation.constructorParent());
                pathChecking = SkeletonCheckResult.INCLUDE;
            }
        }

        if (!pathChecking.equals(SkeletonCheckResult.INCLUDE)){
            Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
            if (maybeAH.isPresent()){
                AnnotationHandler classAnnotationHandler = maybeAH.get();
                if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
                    SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
                    if (classAnnotation.memberParent().length != 0){
                        path = Arrays.asList(classAnnotation.constructorParent());
                        pathChecking = SkeletonCheckResult.INCLUDE;
                    }
                }
            }
        }
    }
}
