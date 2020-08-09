package org.KasymbekovPN.Skeleton.lib.processing.result;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

import java.util.Map;

public interface TaskResult extends Result {
    void put (EntityItem handlerResultId, HandlerResult handlerResult);
    Map<EntityItem, HandlerResult> getResults();
    HandlerResult get(EntityItem handlerResultId);
}
