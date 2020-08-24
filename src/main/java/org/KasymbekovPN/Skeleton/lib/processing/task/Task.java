package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public interface Task<T> {
    AggregateResult handle(T object);
//    Task<T> add(EntityItem wrapperId, TaskWrapper<T> taskWrapper);
//    Result getResult(EntityItem wrapperId);
    //<
    Task<T> add(String wrapperId, TaskWrapper<T> taskWrapper);
    Result getResult(String wrapperId);
}
