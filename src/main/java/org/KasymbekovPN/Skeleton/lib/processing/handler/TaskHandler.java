package org.KasymbekovPN.Skeleton.lib.processing.handler;

import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public interface TaskHandler<T> {
    //< ??? del task
    SimpleResult handle(T object, Task<T> task);
    SimpleResult getResult();
}
