package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.Optional;

public interface MultiChecker<K, T> extends SimpleChecker<T> {
    boolean check(K key, T checkableValue);
    Optional<K> checkByAll(T checkableValue);
}
