package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Triple;

public interface Des2NodeContext extends Context {
    Des2NodeCharItr iterator();
    Finder getFinder();
    void runProcessor();
    void setMode(Des2NodeMode mode);
    void setNode(Node node);
    Node getNode();
    void setParent(Node parent);
    Node getParent();
    Converter<Node, Triple<Node, String, Des2NodeMode>> getConverter();
}
