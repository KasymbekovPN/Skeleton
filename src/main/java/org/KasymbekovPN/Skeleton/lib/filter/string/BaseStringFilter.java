package org.KasymbekovPN.Skeleton.lib.filter.string;

import org.KasymbekovPN.Skeleton.lib.filter.Filter;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseStringFilter implements Filter<String> {

    protected Filter<String> nextFilter;

    @Override
    public Deque<String> filter(Deque<String> rawDeq) {
        if (nextFilter != null){
            return filterImpl(nextFilter.filter(rawDeq));
        }
        return filterImpl(rawDeq);
    }

    @Override
    public void addFilter(Filter<String> filter) {
        if (nextFilter == null){
            nextFilter = filter;
        } else {
            nextFilter.addFilter(filter);
        }
    }

    protected Deque<String> filterImpl(Deque<String> rawDeq){
        return new ArrayDeque<>();
    }
}
