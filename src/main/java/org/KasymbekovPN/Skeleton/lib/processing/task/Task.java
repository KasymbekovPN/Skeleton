package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;

public interface Task<T> {
    TaskResult handle(T object);
    Task<T> add(EntityItem wrapperId, TaskWrapper<T> taskWrapper);
    TaskResult getResult(EntityItem wrapperId);
}
