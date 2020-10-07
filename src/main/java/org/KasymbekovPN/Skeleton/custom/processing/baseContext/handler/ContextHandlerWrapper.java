package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class ContextHandlerWrapper<T extends Context> implements TaskWrapper<T> {

    private final Task<T> task;
    private final TaskHandler<T> taskHandler;

    public ContextHandlerWrapper(Task<T> task,
                                 TaskHandler<T> taskHandler,
                                 String wrapperId) {
        this.task = task;
        this.taskHandler = taskHandler;

        this.task.add(wrapperId, this);
    }

    @Override
    public SimpleResult handle(T object) throws InvocationTargetException,
                                                NoSuchMethodException,
                                                InstantiationException,
                                                IllegalAccessException {
        return taskHandler.handle(object, task);
    }

    @Override
    public SimpleResult getResult() {
        return taskHandler.getResult();
    }
}
