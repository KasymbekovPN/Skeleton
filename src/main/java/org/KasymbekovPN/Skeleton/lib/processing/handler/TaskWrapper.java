package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public interface TaskWrapper<T> {
    SimpleResult handle(T object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    SimpleResult getResult();
}
