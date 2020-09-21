package org.KasymbekovPN.Skeleton.lib.serialization.instance.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public interface InstanceSerializer {
    SimpleResult serialize(Object instance) throws IllegalAccessException;
    Collector getCollector();
    Collector attachCollector(Collector collector);
    void addClassNode(ObjectNode classNode);
    String getId();
}
