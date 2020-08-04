package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllowedClassChecker implements SimpleChecker<Class<?>> {

    private final Set<Class<?>> classes;

    public AllowedClassChecker(Set<Class<?>> classes) {
        this.classes = classes;
    }

    public AllowedClassChecker(Class<?>... classes) {
        this.classes = new HashSet<>(Arrays.asList(classes));
    }

    @Override
    public boolean check(Class<?> checkableValue) {
        return classes.contains(checkableValue);
    }
}
