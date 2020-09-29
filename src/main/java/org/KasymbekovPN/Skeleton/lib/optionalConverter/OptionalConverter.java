package org.KasymbekovPN.Skeleton.lib.optionalConverter;

import java.util.Optional;

public interface OptionalConverter<R, T> {
    Optional<R> convert(T object);
}
