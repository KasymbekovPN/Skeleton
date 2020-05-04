package org.KasymbekovPN.Skeleton.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class SimpleAnnotationHandler implements AnnotationHandler {

    @Override
    public Optional<Annotation> check(Annotation[] annotations, Class<? extends Annotation> annotationType) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(annotationType)){
                return Optional.of(annotation);
            }
        }
        return Optional.empty();
    }
}
