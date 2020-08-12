package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.Result;

public interface TaskWrapper<T> {
    Result handle(T object);
    Result getResult();
}
