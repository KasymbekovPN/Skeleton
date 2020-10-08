package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.InvocationTargetException;

public interface Task<T> {
    Result handle(T object) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    Task<T> add(TaskHandler<T> taskHandler);
    Result getTaskResult();
    String getId();
}
