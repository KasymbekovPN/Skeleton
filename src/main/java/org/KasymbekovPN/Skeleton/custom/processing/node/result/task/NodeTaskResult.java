package org.KasymbekovPN.Skeleton.custom.processing.node.result.task;

import org.KasymbekovPN.Skeleton.custom.processing.node.result.handler.CommonNodeHandlerResult;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;

import java.util.HashMap;
import java.util.Map;

public class NodeTaskResult implements TaskResult {

    private final Map<EntityItem, HandlerResult> results = new HashMap<>();

    @Override
    public void put(EntityItem handlerResultId, HandlerResult handlerResult) {
        results.put(handlerResultId, handlerResult);
    }

    @Override
    public Map<EntityItem, HandlerResult> getResults() {
        return results;
    }

    @Override
    public HandlerResult get(EntityItem handlerResultId) {
        return results.getOrDefault(handlerResultId, new CommonNodeHandlerResult("Not exists"));
    }

    @Override
    public void reset() {
        results.clear();
    }
}
