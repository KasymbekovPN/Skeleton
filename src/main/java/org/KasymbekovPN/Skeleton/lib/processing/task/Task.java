package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public interface Task<T> {
    AggregateResult handle(T object);
    Task<T> add(EntityItem wrapperId, TaskWrapper<T> taskWrapper);
    Result getResult(EntityItem wrapperId);
}
