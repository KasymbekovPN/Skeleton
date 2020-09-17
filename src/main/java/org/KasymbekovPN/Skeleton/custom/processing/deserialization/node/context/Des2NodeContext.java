package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;

public interface Des2NodeContext extends Context {
    Des2NodeCharItr iterator();
    Finder getFinder();
    void runProcessor();
    void setMode(Des2NodeMode mode);
    void setNode(Node node);
    Node getNode();
    void setParent(Node parent);
    Node getParent();
}
