package org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.Deserializer;

public interface NodeDeserializer extends Deserializer {
    void deserialize(Collector collector);
    //<
//    Node handle(SerializedDataWrapper serializedDataWrapper);
//    void addHandler(EntityItem handlerId, NodeDeserializerHandler handler);
}
