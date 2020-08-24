package org.KasymbekovPN.Skeleton.custom.processing.node.handler;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class NodeProcessHandlerWrapper implements TaskWrapper<Node> {

    private final Task<Node> task;
    private final TaskHandler<Node> taskHandler;
    private final EntityItem ei;

    private Result wrongResult;

    public NodeProcessHandlerWrapper(Task<Node> task,
                                     TaskHandler<Node> taskHandler,
                                     EntityItem ei,
                                     Result wrongResult) {
        this.task = task;
        this.taskHandler = taskHandler;
        this.ei = ei;
        this.wrongResult = wrongResult;

        this.task.add(ei.toString(), this);
    }

    @Override
    public Result handle(Node object) {
        return object.getEI().equals(ei)
                ? taskHandler.handle(object, task)
                : getWrongResult("wrong object type");
    }

    @Override
    public Result getResult() {
        return taskHandler != null
                ? taskHandler.getHandlerResult()
                : getWrongResult("handler is null");
    }

    private Result getWrongResult(String status){
        Result newWrongResult = wrongResult.createNew();
        newWrongResult.setStatus(status);

        return newWrongResult;
    }
}