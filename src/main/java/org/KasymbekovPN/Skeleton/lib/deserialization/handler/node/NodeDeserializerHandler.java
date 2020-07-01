package org.KasymbekovPN.Skeleton.lib.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.DeserializerHandler;

public interface NodeDeserializerHandler extends DeserializerHandler {
    Node handle(String rawData);
}
