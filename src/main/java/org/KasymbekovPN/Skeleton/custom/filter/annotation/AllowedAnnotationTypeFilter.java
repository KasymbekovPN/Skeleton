package org.KasymbekovPN.Skeleton.custom.filter.annotation;

import org.KasymbekovPN.Skeleton.lib.filter.annotation.BaseAnnotationFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class AllowedAnnotationTypeFilter extends BaseAnnotationFilter {

    private final Set<Class<? extends Annotation>> allowedAnnotationTypes;

    public AllowedAnnotationTypeFilter(Set<Class<? extends Annotation>> allowedAnnotationTypes) {
        this.allowedAnnotationTypes = allowedAnnotationTypes;
    }

    @SafeVarargs
    public AllowedAnnotationTypeFilter(Class<? extends Annotation>... allowedAnnotationTypes) {
        this.allowedAnnotationTypes = new HashSet<>(Arrays.asList(allowedAnnotationTypes));
    }

    @Override
    protected Deque<Annotation> filterImpl(Deque<Annotation> rawDeq) {
        int size = rawDeq.size();
        for (int i = 0; i < size; i++) {
            Annotation annotation = rawDeq.pollLast();
            if (allowedAnnotationTypes.contains(annotation.annotationType())){
                rawDeq.push(annotation);
            }
        }

        return rawDeq;
    }
}
