package org.KasymbekovPN.Skeleton.annotation.handler.clazz;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
//import org.KasymbekovPN.Skeleton.annotation.handlerContainer.AnnotationHandlerContainer;
//import org.KasymbekovPN.Skeleton.annotation.handlerContainer.Entity;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClassAH implements AnnotationHandler {

//    private final AnnotationHandlerContainer annotationHandlerContainer;
    //<
    private SkeletonCheckResult annotationChecking;
    private SkeletonCheckResult pathChecking;
    private SkeletonClass annotation;
    private List<String> path;

    public ClassAH() {
        resetCheckResult();
    }

    //<
//    public ClassAH(AnnotationHandlerContainer annotationHandlerContainer) {
////        annotationHandlerContainer.setClassAnnotationHandler(this);
//        //<
//        this.annotationHandlerContainer = annotationHandlerContainer;
//        this.annotationHandlerContainer.setAnnotationHandler(Entity.CLASS, this);
//        resetCheckResult();
//    }

    @Override
    public Optional<Annotation> check(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonClass.class)) {
                annotationChecking = SkeletonCheckResult.INCLUDE;
                this.annotation = (SkeletonClass) annotation;

                return Optional.of(this.annotation);
            }
        }

        return Optional.empty();
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

    //<
//    @Override
//    public AnnotationHandlerContainer getContainer() {
//        return annotationHandlerContainer;
//    }

    private void extractPath(){
        if (annotationChecking.equals(SkeletonCheckResult.INCLUDE)){
            path = Arrays.asList(annotation.classParent());
            pathChecking = path.size() == 0 ? SkeletonCheckResult.EXCLUDE : SkeletonCheckResult.INCLUDE;
        } else {
            pathChecking = SkeletonCheckResult.EXCLUDE;
        }
    }
}
