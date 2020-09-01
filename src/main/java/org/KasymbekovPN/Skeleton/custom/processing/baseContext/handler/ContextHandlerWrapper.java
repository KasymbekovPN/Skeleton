package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class ContextHandlerWrapper implements TaskWrapper<Context> {

    private static final String HANDLER_IS_NULL = "Handler is null";

    private final Task<Context> task;
    private final TaskHandler<Context> taskHandler;

    private Result wrongResult;

    public ContextHandlerWrapper(Task<Context> task,
                                 TaskHandler<Context> taskHandler,
                                 String wrapperId,
                                 Result wrongResult) {
        this.task = task;
        this.taskHandler = taskHandler;
        this.wrongResult = wrongResult;

        this.task.add(wrapperId, this);
    }

    @Override
    public Result handle(Context object) {
        return taskHandler.handle(object, task);
    }

    @Override
    public Result getResult() {
        return taskHandler != null
                ? taskHandler.getResult()
                : getWrongResult(HANDLER_IS_NULL);
    }

    private Result getWrongResult(String status){
        Result newWrongResult = wrongResult.createNew();
        newWrongResult.setStatus(status);

        return newWrongResult;
    }
}
