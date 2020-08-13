package org.KasymbekovPN.Skeleton.custom.processing.node.task;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashMap;
import java.util.Map;

public class NodeTask implements Task<Node> {

    private final Map<EntityItem, TaskWrapper<Node>> wrappers = new HashMap<>();
    private final AggregateResult taskResult;

    private Result wrongResult;

    public NodeTask(AggregateResult taskResult,
                    Result wrongResult) {
        this.taskResult = taskResult;
        this.wrongResult = wrongResult;
    }

    @Override
    public AggregateResult handle(Node object) {
        EntityItem ei = object.getEI();
        Result handlerResult = wrappers.containsKey(ei)
                ? wrappers.get(ei).handle(object)
                : getWrongResult("wrapper for " + ei + " doesn't exist");
        taskResult.put(ei.toString(), handlerResult);

        return taskResult;
    }

    @Override
    public Task<Node> add(EntityItem wrapperId, TaskWrapper<Node> taskWrapper) {
        wrappers.put(wrapperId, taskWrapper);
        return this;
    }

    @Override
    public Result getResult(EntityItem wrapperId) {
        return taskResult.get(wrapperId.toString());
    }

    private Result getWrongResult(String status){
        Result newWrongResult = wrongResult.createNew();
        newWrongResult.setStatus(status);

        return newWrongResult;
    }
}
