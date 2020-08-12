package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public interface TaskHandler<T> {
    //< ??? del task
    Result handle(T object, Task<T> task);
    Result getHandlerResult();
}
