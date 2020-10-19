package org.KasymbekovPN.Skeleton.lib.processing.task;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.InvocationTargetException;

public interface Task<T> {
    Result handle(T object) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
    Task<T> add(TaskHandler<T> taskHandler);
    Result getTaskResult() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    String getId();
}
