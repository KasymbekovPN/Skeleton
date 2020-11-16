package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.Optional;

public interface MultiChecker<K, T> extends SimpleChecker<T> {
    void setKey(K key);

    // TODO: 16.11.2020 test
    Optional<K> checkByAll(T checkedValue);
}
