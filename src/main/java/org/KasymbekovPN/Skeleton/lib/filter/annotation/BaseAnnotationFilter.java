package org.KasymbekovPN.Skeleton.lib.filter.annotation;

import org.KasymbekovPN.Skeleton.lib.filter.Filter;

import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseAnnotationFilter implements Filter<Annotation> {

    protected Filter<Annotation> nextFilter;

    @Override
    public Deque<Annotation> filter(Deque<Annotation> rawDeq) {
        if (nextFilter != null){
            return filterImpl(nextFilter.filter(rawDeq));
        }
        return filterImpl(rawDeq);
    }

    @Override
    public void addFilter(Filter<Annotation> filter) {
        if (nextFilter == null){
            nextFilter = filter;
        } else {
            nextFilter.addFilter(filter);
        }
    }

    protected Deque<Annotation> filterImpl(Deque<Annotation> rawDeq){
        return new ArrayDeque<>();
    }
}
