package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SkeletonCollectorWritingProcess implements CollectorProcess {

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorWritingProcess.class);

    private Map<EntityItem, CollectorProcessHandler> handlers = new HashMap<>();

    @Override
    public void handle(Node node) {
        EntityItem ei = node.getEI();
        if (handlers.containsKey(ei)){
            handlers.get(ei).handle(node);
        } else {
            log.error("The handler for {} doesn't exist", ei);
        }
    }

    @Override
    public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
        handlers.put(handlerId, collectorProcessHandler);
    }
}