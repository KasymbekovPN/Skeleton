package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.KasymbekovPN.Skeleton.lib.filter.string.BaseStringFilter;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class AllowedStringFilter extends BaseStringFilter {

    private final Set<String> allowedStrings;

    public AllowedStringFilter(Set<String> allowedStrings) {
        this.allowedStrings = allowedStrings;
    }

    public AllowedStringFilter(String... allowedStrings) {
        this.allowedStrings = new HashSet<>(Arrays.asList(allowedStrings));
    }

    @Override
    protected Deque<String> filterImpl(Deque<String> rawDeq) {
        int size = rawDeq.size();
        for (int i = 0; i < size; i++) {
            String lastElement = rawDeq.pollLast();
            if (allowedStrings.contains(lastElement)){
                rawDeq.push(lastElement);
            }
        }

        return rawDeq;
    }
}
