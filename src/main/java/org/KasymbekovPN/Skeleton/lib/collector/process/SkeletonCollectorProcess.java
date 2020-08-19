package org.KasymbekovPN.Skeleton.lib.collector.process;

//< del !!!
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SkeletonCollectorProcess implements CollectorProcess{
//
//    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorProcess.class);
//
//    private final Map<EntityItem, CollectorProcessHandler> handlers = new HashMap<>();
//
//    public SkeletonCollectorProcess() {
//    }
//
//    @Override
//    public void handle(Node node) {
//        EntityItem ei = node.getEI();
//        if (handlers.containsKey(ei)){
//            handlers.get(ei).handle(node);
//        } else {
//            log.error("The handler for {} doesn't exist", ei);
//        }
//    }
//
//    @Override
//    public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
//        handlers.put(handlerId, collectorProcessHandler);
//    }
//}
