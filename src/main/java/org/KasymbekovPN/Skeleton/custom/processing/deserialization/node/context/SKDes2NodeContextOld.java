package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

public class SKDes2NodeContextOld implements Des2NodeContextOld {

    private final EnumMap<Des2NodeModeOld, ContextIds> contextIds;
    private final Des2NodeCharItrOld iterator;
    private final FinderOld finderOld;
    private final OldContextProcessor<Des2NodeContextOld> processor;
    private final Converter<Node, Triple<Node, String, Des2NodeModeOld>> converter;

    private Des2NodeModeOld mode = Des2NodeModeOld.INIT;
    private Node currentNode;
    private Node parent;

    public SKDes2NodeContextOld(EnumMap<Des2NodeModeOld, ContextIds> contextIds,
                                Des2NodeCharItrOld iterator,
                                FinderOld finderOld,
                                OldContextProcessor<Des2NodeContextOld> processor,
                                Converter<Node, Triple<Node, String, Des2NodeModeOld>> converter) {
        this.contextIds = contextIds;
        this.iterator = iterator;
        this.finderOld = finderOld;
        this.processor = processor;
        this.converter = converter;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds.get(mode);
    }

    @Override
    public Des2NodeCharItrOld iterator() {
        return iterator;
    }

    @Override
    public FinderOld getFinderOld() {
        return finderOld;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        processor.handle(this);
    }

    @Override
    public void setMode(Des2NodeModeOld mode) {
        this.mode = mode;
    }

    @Override
    public void setNode(Node node) {
        this.currentNode = node;
    }

    @Override
    public Node getNode() {
        return currentNode;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Converter<Node, Triple<Node, String, Des2NodeModeOld>> getConverter() {
        return converter;
    }
}
