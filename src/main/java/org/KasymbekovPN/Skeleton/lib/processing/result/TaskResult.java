package org.KasymbekovPN.Skeleton.lib.processing.result;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

public interface TaskResult extends Result {
    void put (EntityItem resultId, HandlerResult handlerResult);
}
