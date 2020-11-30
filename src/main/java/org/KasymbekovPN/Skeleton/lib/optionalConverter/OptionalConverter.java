package org.KasymbekovPN.Skeleton.lib.optionalConverter;

import java.util.Optional;

// TODO: 30.11.2020 del
public interface OptionalConverter<R, T> {
    Optional<R> convert(T object);
}
