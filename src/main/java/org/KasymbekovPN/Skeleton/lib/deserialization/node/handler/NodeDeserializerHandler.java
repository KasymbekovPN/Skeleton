package org.KasymbekovPN.Skeleton.lib.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.lib.node.Node;

public interface NodeDeserializerHandler {
    //< ??? rename method run ???
    NodeDeserializerHandler run();
    default void setChildNode(Node node){}
    default Node getNode() {return null;}
}
