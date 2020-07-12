package org.KasymbekovPN.Skeleton.lib.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

public interface NodeDeserializerHandler {
    NodeDeserializerHandler run();
    default void setChildNode(Node node){}
    default Node getNode() {return null;}
}
