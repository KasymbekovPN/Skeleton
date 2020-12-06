package org.KasymbekovPN.Skeleton.custom.extractor.annotation;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;

import java.lang.annotation.Annotation;
import java.util.Optional;

// TODO: 05.12.2020 change with functional interface
public class ClassNameExtractor implements Extractor<String, Annotation[]> {

    @Override
    public Optional<String> extract(Annotation[] object) {

        for (Annotation annotation : object) {
            if (annotation.annotationType().equals(SkeletonClass.class)){
                SkeletonClass castedAnnotation = (SkeletonClass) annotation;
                return Optional.of(castedAnnotation.name());
            }
        }

        return Optional.empty();
    }
}
