package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public interface TaskWrapper<T> {
    SimpleResult handle(T object);
    SimpleResult getResult();
}
