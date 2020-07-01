package org.KasymbekovPN.Skeleton.lib.collector.process;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SkeletonCollectorProcess implements CollectorProcess{

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorProcess.class);

    private final Map<Class<? extends Node>, CollectorProcessHandler> handlers = new HashMap<>();

    public SkeletonCollectorProcess() {
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
}
