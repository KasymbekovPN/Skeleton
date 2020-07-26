package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.HashMap;
import java.util.Map;

class TestCollectorCheckingProcess implements CollectorCheckingProcess {

    private final Map<EntityItem, CollectorProcessHandler> handlers = new HashMap<>();
    private final Map<EntityItem, CollectorCheckingResult> results = new HashMap<>();

    @Override
    public void handle(Node node) {
        EntityItem ei = node.getEI();
        if (handlers.containsKey(ei)){
            handlers.get(ei).handle(node);
        }
    }

    @Override
    public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
        handlers.put(handlerId, collectorProcessHandler);
    }

    @Override
    public void setResult(EntityItem handlerId, CollectorCheckingResult result) {
        results.put(handlerId, result);
    }

    @Override
    public CollectorCheckingResult getResult() {
        if (results.containsValue(CollectorCheckingResult.EXCLUDE)){
            return CollectorCheckingResult.EXCLUDE;
        } else if (results.containsValue(CollectorCheckingResult.INCLUDE)) {
            return CollectorCheckingResult.INCLUDE;
        }
        return CollectorCheckingResult.NONE;
    }
}
