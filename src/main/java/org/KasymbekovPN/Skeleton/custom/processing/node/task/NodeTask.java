package org.KasymbekovPN.Skeleton.custom.processing.node.task;

import org.KasymbekovPN.Skeleton.custom.processing.node.result.handler.CommonNodeHandlerResult;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

import java.util.HashMap;
import java.util.Map;

public class NodeTask implements Task<Node> {

    private final Map<EntityItem, TaskWrapper<Node>> wrappers = new HashMap<>();
    private final TaskResult taskResult;

    public NodeTask(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    @Override
    public TaskResult handle(Node object) {
        EntityItem ei = object.getEI();
        HandlerResult handlerResult = wrappers.containsKey(ei)
                ? wrappers.get(ei).handle(object)
                : new CommonNodeHandlerResult("wrapper for " + ei + " doesn't exist");
        taskResult.put(ei, handlerResult);

        return taskResult;
    }

    @Override
    public Task<Node> add(EntityItem wrapperId, TaskWrapper<Node> taskWrapper) {
        wrappers.put(wrapperId, taskWrapper);
        return this;
    }

    @Override
    public TaskResult getResult(EntityItem wrapperId) {
        return taskResult;
    }
}
