package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public interface TaskHandler<T> {
    SimpleResult handle(T object) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    SimpleResult getResult();
    String getId();
}
