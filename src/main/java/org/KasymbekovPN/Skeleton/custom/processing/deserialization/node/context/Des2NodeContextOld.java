package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.OldContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.InvocationTargetException;

public interface Des2NodeContextOld extends OldContext {
    Des2NodeCharItrOld iterator();
    FinderOld getFinderOld();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
    void setMode(Des2NodeModeOld mode);
    void setNode(Node node);
    Node getNode();
    void setParent(Node parent);
    Node getParent();
    Converter<Node, Triple<Node, String, Des2NodeModeOld>> getConverter();
}
