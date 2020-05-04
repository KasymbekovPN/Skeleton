package org.KasymbekovPN.Skeleton.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface AnnotationHandler {
    Optional<Annotation> check(Annotation[] annotations, Class<? extends Annotation> annotationType);
}
