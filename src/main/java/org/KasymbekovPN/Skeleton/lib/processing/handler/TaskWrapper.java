package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;

public interface TaskWrapper<T> {
    HandlerResult handle(T object);
    HandlerResult getResult();
}
