package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllowedStringChecker implements SimpleChecker<String> {

    private final Set<String> classNames;

    public AllowedStringChecker(Set<String> classNames) {
        this.classNames = classNames;
    }

    public AllowedStringChecker(String... classNames) {
        this.classNames = new HashSet<>(Arrays.asList(classNames));
    }

    @Override
    public boolean check(String checkableValue) {
        return classNames.contains(checkableValue);
    }
}
