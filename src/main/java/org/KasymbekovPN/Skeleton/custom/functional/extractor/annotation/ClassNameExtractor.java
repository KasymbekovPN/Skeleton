package org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ClassNameExtractor implements OptFunction<Annotation[], String> {

    @Override
    public Optional<String> apply(Annotation[] var) {
        for (Annotation annotation : var) {
            if (annotation.annotationType().equals(SkeletonClass.class)){
                SkeletonClass castedAnnotation = (SkeletonClass) annotation;
                return Optional.of(castedAnnotation.name());
            }
        }

        return Optional.empty();
    }
}
