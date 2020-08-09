package org.KasymbekovPN.Skeleton.custom.processing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.node.result.handler.CommonNodeHandlerResult;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

public class NodeProcessHandlerWrapper implements TaskWrapper<Node> {

    private final Task<Node> task;
    private final TaskHandler<Node> taskHandler;
    private final EntityItem ei;

    public NodeProcessHandlerWrapper(Task<Node> task,
                                     TaskHandler<Node> taskHandler,
                                     EntityItem ei) {
        this.task = task;
        this.taskHandler = taskHandler;
        this.ei = ei;

        this.task.add(ei, this);
    }

    @Override
    public HandlerResult handle(Node object) {
        return object.getEI().equals(ei)
                ? taskHandler.handle(object, task)
                : new CommonNodeHandlerResult("wrong object type");
    }

    @Override
    public HandlerResult getResult() {
        return taskHandler != null
                ? taskHandler.getHandlerResult()
                : new CommonNodeHandlerResult("handler is null");
    }
}