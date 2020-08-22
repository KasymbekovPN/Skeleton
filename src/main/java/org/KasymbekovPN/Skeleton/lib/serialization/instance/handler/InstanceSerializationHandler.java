package org.KasymbekovPN.Skeleton.lib.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

public interface InstanceSerializationHandler {
    InstanceSerializationHandler setNext(InstanceSerializationHandler next);
    void handle(Object object, Collector collector, ObjectNode classNode);
}
