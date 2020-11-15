package org.KasymbekovPN.Skeleton.lib.checker;

public interface MultiChecker<K, T> extends SimpleChecker<T> {
    void setKey(K key);
}
