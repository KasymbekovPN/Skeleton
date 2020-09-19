package org.KasymbekovPN.Skeleton.lib.converter;

public interface Converter<R, T> {
    R convert(T value);
}
