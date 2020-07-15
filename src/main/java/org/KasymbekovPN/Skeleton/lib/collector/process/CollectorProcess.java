package org.KasymbekovPN.Skeleton.lib.collector.process;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public interface CollectorProcess {
    void handle(Node node);
    //< !!! replace clazz type with NodeEI
//    void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler);
    //<
    void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler);
}
