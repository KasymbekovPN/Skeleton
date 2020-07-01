package org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.Deserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public interface NodeDeserializer extends Deserializer {
    Node handle(String rawData);
    void addHandler(EntityItem handlerId, NodeDeserializerHandler handler);
}
