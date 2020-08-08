package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

public interface TaskHandler<T> {
    HandlerResult handle(T object, Task<T> task);
    HandlerResult getResult();
}
