package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.StringChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IgnoredPropertyNameChecker implements StringChecker {

    private final Set<String> ignoredPropertyNames;

    public IgnoredPropertyNameChecker() {
        this.ignoredPropertyNames = new HashSet<>();
    }

    public IgnoredPropertyNameChecker(Set<String> ignoredPropertyNames) {
        this.ignoredPropertyNames = ignoredPropertyNames;
    }

    public IgnoredPropertyNameChecker(String... names) {
        this.ignoredPropertyNames = new HashSet<>(Arrays.asList(names));
    }

    @Override
    public boolean check(String string) {
        return !ignoredPropertyNames.contains(string);
    }
}
