package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public interface Task<T> {
    Result handle(T object);
    Task<T> add(String wrapperId, TaskWrapper<T> taskWrapper);
    Result getTaskResult();
}
