package org.KasymbekovPN.Skeleton.lib.processing.processor;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface Processor<T> {
    Processor<T> add(Task<T> task);
    Optional<Task<T>> get(String taskId);
    Optional<Task<T>> remove(String taskId);
    Result handle(T object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty;
    Result getProcessorResult() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
