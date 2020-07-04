package org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.Deserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public interface NodeDeserializer extends Deserializer {
    Node handle(SerializedDataWrapper serializedDataWrapper);
    void addHandler(EntityItem handlerId, NodeDeserializerHandler handler);
}
