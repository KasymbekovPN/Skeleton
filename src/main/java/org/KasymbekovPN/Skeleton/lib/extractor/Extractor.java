package org.KasymbekovPN.Skeleton.lib.extractor;

import java.util.Optional;

public interface Extractor<R, T> {
    Optional<R> extract(T object);
}
