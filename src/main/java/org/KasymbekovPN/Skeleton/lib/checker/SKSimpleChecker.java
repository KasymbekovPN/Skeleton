package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SKSimpleChecker<T> implements SimpleChecker<T> {

    private final Set<T> allowedValues;

    public SKSimpleChecker(Set<T> allowedValues) {
        this.allowedValues = allowedValues;
    }

    @SafeVarargs
    public SKSimpleChecker(T... allowedValues) {
        this.allowedValues = new HashSet<>(Arrays.asList(allowedValues));
    }

    @Override
    public boolean check(T checkableValue) {
        return allowedValues.contains(checkableValue);
    }
}
