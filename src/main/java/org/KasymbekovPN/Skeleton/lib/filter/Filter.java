package org.KasymbekovPN.Skeleton.lib.filter;

import java.util.Deque;

public interface Filter<T> {
    Deque<T> filter(Deque<T> rawDeq);
    void addFilter(Filter<T> filter);
}
