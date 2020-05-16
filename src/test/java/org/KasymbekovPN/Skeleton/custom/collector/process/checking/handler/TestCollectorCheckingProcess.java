package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;

import java.util.HashMap;
import java.util.Map;

class TestCollectorCheckingProcess implements CollectorCheckingProcess {

    private final Map<Class<? extends Node>, CollectorProcessHandler> handlers = new HashMap<>();
    private final Map<Class<? extends Node>, CollectorCheckingResult> results = new HashMap<>();

    @Override
    public void handle(Node node) {
        Class<? extends Node> clazz = node.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(node);
        }
    }

    @Override
    public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {
        handlers.put(clazz, collectorProcessHandler);
    }

    @Override
    public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {
        results.put(clazz, result);
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
