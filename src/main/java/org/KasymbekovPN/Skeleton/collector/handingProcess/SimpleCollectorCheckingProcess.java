package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleCollectorCheckingProcess implements CollectorCheckingProcess {

    private static final Logger log = LoggerFactory.getLogger(SimpleCollectorCheckingProcess.class);

    private final Map<Class<? extends Node>, CollectorHandlingProcessHandler> handlers = new HashMap<>();
    private final Map<Class<? extends Node>, SkeletonCheckResult> results = new HashMap<>();

    public SimpleCollectorCheckingProcess() {
    }

    @Override
    public void handle(Node node) {
        Class<? extends Node> clazz = node.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(node);
        } else {
            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
        }
    }

    @Override
    public void addHandler(Class<? extends Node> clazz, CollectorHandlingProcessHandler collectorHandlingProcessHandler) {
        handlers.put(clazz, collectorHandlingProcessHandler);
    }

    @Override
    public void setResult(Class<? extends Node> clazz, SkeletonCheckResult result) {
        results.put(clazz, result);
    }

    @Override
    public SkeletonCheckResult getResult(boolean cleanHandlers) {
        if (results.containsValue(SkeletonCheckResult.EXCLUDE)){
            return SkeletonCheckResult.EXCLUDE;
        } else if (results.containsValue(SkeletonCheckResult.INCLUDE)) {
            return SkeletonCheckResult.INCLUDE;
        }
        return SkeletonCheckResult.NONE;
    }
}
