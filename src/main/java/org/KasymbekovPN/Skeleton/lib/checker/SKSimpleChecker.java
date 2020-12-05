package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SKSimpleChecker<T> implements Function<T, Boolean> {

    private final Set<T> allowedValues;

    public SKSimpleChecker(Set<T> allowedValues) {
        this.allowedValues = allowedValues;
    }

    @SafeVarargs
    public SKSimpleChecker(T... allowedValues) {
        this.allowedValues = new HashSet<>(Arrays.asList(allowedValues));
    }

    @Override
    public Boolean apply(T t) {
        return allowedValues.contains(t);
    }
}
