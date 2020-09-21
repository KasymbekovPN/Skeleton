package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class ContextHandlerWrapper implements TaskWrapper<Context> {

    private final Task<Context> task;
    private final TaskHandler<Context> taskHandler;

    public ContextHandlerWrapper(Task<Context> task,
                                 TaskHandler<Context> taskHandler,
                                 String wrapperId) {
        this.task = task;
        this.taskHandler = taskHandler;

        this.task.add(wrapperId, this);
    }

    @Override
    public SimpleResult handle(Context object) {
        return taskHandler.handle(object, task);
    }

    @Override
    public SimpleResult getResult() {
        return taskHandler.getResult();
    }
}
