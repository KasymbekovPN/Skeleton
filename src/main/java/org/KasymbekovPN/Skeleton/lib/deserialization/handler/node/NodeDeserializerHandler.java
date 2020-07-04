package org.KasymbekovPN.Skeleton.lib.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.DeserializerHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Map;
import java.util.Optional;

public interface NodeDeserializerHandler extends DeserializerHandler {
//    Node handle(String rawData);
    //<
    Optional<Node> handle(SerializedDataWrapper serializedDataWrapper, Node parent);
    void setHandlers(Map<EntityItem, NodeDeserializerHandler> handlers);
}
