package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.KasymbekovPN.Skeleton.lib.filter.string.BaseStringFilter;

import java.util.Deque;
import java.util.Set;

public class IgnoreStringFilter extends BaseStringFilter {

    private final Set<String> ignoredStrings;

    public IgnoreStringFilter(Set<String> ignoredStrings) {
        this.ignoredStrings = ignoredStrings;
    }

    @Override
    protected Deque<String> filterImpl(Deque<String> rawDeq) {
        int size = rawDeq.size();
        for (int i = 0; i < size; i++) {
            String last = rawDeq.pollLast();
            if (!ignoredStrings.contains(last)){
                rawDeq.push(last);
            }
        }

        return rawDeq;
    }
}
