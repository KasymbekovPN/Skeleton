package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceData;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class InstanceHandlerWrapper implements TaskWrapper<InstanceData> {

    private final Task<InstanceData> task;
    private final TaskHandler<InstanceData> taskHandler;

    private Result wrongResult;

    public InstanceHandlerWrapper(Task<InstanceData> task,
                                  TaskHandler<InstanceData> taskHandler,
                                  String wrapperId,
                                  Result wrongResult) {
        this.task = task;
        this.taskHandler = taskHandler;
        this.wrongResult = wrongResult;

        this.task.add(wrapperId, this);
    }

    @Override
    public Result handle(InstanceData object) {
        return taskHandler.handle(object, task);
    }

    @Override
    public Result getResult() {
        return taskHandler != null
                ? taskHandler.getHandlerResult()
                : getWrongResult("Handler is null");
    }

    private Result getWrongResult(String status){
        Result newWrongResult = wrongResult.createNew();
        newWrongResult.setStatus(status);

        return newWrongResult;
    }
}
