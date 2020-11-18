package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.Optional;

public interface MultiChecker<K, T> extends SimpleChecker<T> {
    void setKey(K key);
    Optional<K> checkByAll(T checkedValue);
}
