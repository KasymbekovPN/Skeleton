package org.KasymbekovPN.Skeleton.condition;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClassACH implements AnnotationConditionHandler {

    private SkeletonClass annotation;

    @Override
    public MemberCheckResult check(Annotation[] annotations) {
        Optional<SkeletonClass> maybeAnnotation = extractAnnotation(annotations);
        if (maybeAnnotation.isPresent()){
            annotation = maybeAnnotation.get();
            return MemberCheckResult.INCLUDE;
        }

        return MemberCheckResult.EXCLUDE;
    }

    @Override
    public Optional<List<String>> getParentPath(Annotation[] annotations) {
        Optional<SkeletonClass> maybeAnnotation = extractAnnotation(annotations);
        if (maybeAnnotation.isPresent() && maybeAnnotation.get().classParent().length != 0){
            return Optional.of(Arrays.asList(maybeAnnotation.get().classParent()));
        }

        return Optional.empty();
    }

    @Override
    public Annotation getAnnotation() {
        return annotation;
    }

    private Optional<SkeletonClass> extractAnnotation(Annotation[] annotations){

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonClass.class)){
                return Optional.of((SkeletonClass) annotation);
            }
        }
        return Optional.empty();
    }
}
