package org.KasymbekovPN.Skeleton.lib.serialization.instance.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.Node;

public interface InstanceSerializer {
    void serialize(Object instance);
    Collector getCollector();
    Collector attachCollector(Collector collector);
    Node attachClassNode(String classNodeId, Node classNode);
    Node detachClassNode(String classNodeId);
    String getId();
}
