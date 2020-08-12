package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;

public interface Task<T> {
    AggregateResult handle(T object);
    Task<T> add(EntityItem wrapperId, TaskWrapper<T> taskWrapper);
    AggregateResult getResult(EntityItem wrapperId);
}
