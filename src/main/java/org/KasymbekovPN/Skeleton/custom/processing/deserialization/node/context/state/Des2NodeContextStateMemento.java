package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

public interface Des2NodeContextStateMemento extends ContextStateMemento {
    void setNode(Node node);
    Node getNode();
    Node getParentNode();
    void setKey(Object key);
    Object getKey();
}
