package org.KasymbekovPN.Skeleton.lib.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface AnnotationChecker {
    Optional<Annotation> check(Annotation[] annotations, Class<? extends Annotation> annotationType);
    Optional<Annotation> check(Annotation[] annotations, Class<? extends Annotation> annotationType,
                               Class<? extends Annotation> annotatingType);
}
