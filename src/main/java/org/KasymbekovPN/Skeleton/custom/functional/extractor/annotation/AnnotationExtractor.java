package org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation;

import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AnnotationExtractor implements OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> {

    @Override
    public Optional<Annotation> apply(Pair<Class<? extends Annotation>, Annotation[]> var) {
        Class<? extends Annotation> type = var.getLeft();
        Annotation[] annotations = var.getRight();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(type)){
                return  Optional.of(annotation);
            }
        }

        return Optional.empty();
    }
}
