package org.KasymbekovPN.Skeleton.condition;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ClassAH implements AnnotationHandler {

    private SkeletonCheckResult nameChecking;
    private SkeletonCheckResult pathChecking;
    private SkeletonClass annotation;
    private List<String> path;

    public ClassAH(AnnotationHandlerContainer annotationHandlerContainer) {
        annotationHandlerContainer.setClassAnnotationHandler(this);
        resetCheckResult();
    }

    @Override
    public void check(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonClass.class)) {
                nameChecking = SkeletonCheckResult.INCLUDE;
                this.annotation = (SkeletonClass) annotation;
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

        return nameChecking.equals(SkeletonCheckResult.INCLUDE) && pathChecking.equals(SkeletonCheckResult.INCLUDE)
                ? SkeletonCheckResult.INCLUDE
                : SkeletonCheckResult.EXCLUDE;
    }

    @Override
    public void resetCheckResult() {
        nameChecking = SkeletonCheckResult.NONE;
        pathChecking = SkeletonCheckResult.NONE;
    }

    @Override
    public List<String> getPath() {
        return path;
    }

    private void extractPath(){
        if (nameChecking.equals(SkeletonCheckResult.INCLUDE)){
            path = Arrays.asList(annotation.classParent());
            pathChecking = path.size() == 0 ? SkeletonCheckResult.EXCLUDE : SkeletonCheckResult.INCLUDE;
        } else {
            pathChecking = SkeletonCheckResult.EXCLUDE;
        }
    }
}
