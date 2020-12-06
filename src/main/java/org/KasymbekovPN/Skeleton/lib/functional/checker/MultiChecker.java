package org.KasymbekovPN.Skeleton.lib.functional.checker;

import java.util.Optional;
import java.util.function.Function;

public interface MultiChecker<K, T> extends Function<T, Boolean> {
    boolean apply(K key, T checkableValue);
    Optional<K> applyByAll(T checkableValue);
}
