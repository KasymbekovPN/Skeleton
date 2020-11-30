package org.KasymbekovPN.Skeleton.lib.functional;

import java.util.Optional;

@FunctionalInterface
public interface OptFunction<T, R> {
    Optional<R> apply(T var);
}
