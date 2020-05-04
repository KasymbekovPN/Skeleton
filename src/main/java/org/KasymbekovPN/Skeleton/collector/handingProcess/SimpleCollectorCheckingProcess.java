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

    private SkeletonCheckResult result = SkeletonCheckResult.NONE;

    @Override
    public void doIt(Node node) {
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
    public void setResult(SkeletonCheckResult result) {
        this.result = result;
    }

    @Override
    public SkeletonCheckResult getResult() {
        return result;
    }

    @Override
    public SkeletonCheckResult getResult(boolean cleanHandlers) {
        handlers.clear();
        return result;
    }
}
