package org.KasymbekovPN.Skeleton.custom.extractor.annotation;

import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Optional;

// TODO: 22.11.2020 test
public class AnnotationExtractor implements Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> {

    @Override
    public Optional<Annotation> extract(Pair<Class<? extends Annotation>, Annotation[]> object) {
        Class<? extends Annotation> type = object.getLeft();
        Annotation[] annotations = object.getRight();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(type)){
                return  Optional.of(annotation);
            }
        }

        return Optional.empty();
    }
}
