package org.KasymbekovPN.Skeleton.lib.collector.process.checking;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SkeletonCollectorCheckingProcess implements CollectorCheckingProcess {

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorCheckingProcess.class);

    private final Map<Class<? extends Node>, CollectorProcessHandler> handlers = new HashMap<>();
    private final Map<Class<? extends Node>, CollectorCheckingResult> results = new HashMap<>();

    public SkeletonCollectorCheckingProcess() {
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
    public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {
        handlers.put(clazz, collectorProcessHandler);
    }

    @Override
    public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {
        results.put(clazz, result);
    }

    @Override
    public CollectorCheckingResult getResult(boolean cleanHandlers) {
        if (results.containsValue(CollectorCheckingResult.EXCLUDE)){
            return CollectorCheckingResult.EXCLUDE;
        } else if (results.containsValue(CollectorCheckingResult.INCLUDE)) {
            return CollectorCheckingResult.INCLUDE;
        }
        return CollectorCheckingResult.NONE;
    }
}
