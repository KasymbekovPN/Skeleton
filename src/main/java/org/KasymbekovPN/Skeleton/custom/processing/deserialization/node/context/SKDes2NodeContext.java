package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;

public class SKDes2NodeContext implements Des2NodeContext {

    private final EnumMap<Des2NodeMode, ContextIds> contextIds;
    private final Des2NodeCharItr iterator;
    private final Finder finder;
    private final OldContextProcessor<Des2NodeContext> processor;
    private final Converter<Node, Triple<Node, String, Des2NodeMode>> converter;

    private Des2NodeMode mode = Des2NodeMode.INIT;
    private Node currentNode;
    private Node parent;

    public SKDes2NodeContext(EnumMap<Des2NodeMode, ContextIds> contextIds,
                             Des2NodeCharItr iterator,
                             Finder finder,
                             OldContextProcessor<Des2NodeContext> processor,
                             Converter<Node, Triple<Node, String, Des2NodeMode>> converter) {
        this.contextIds = contextIds;
        this.iterator = iterator;
        this.finder = finder;
        this.processor = processor;
        this.converter = converter;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds.get(mode);
    }

    @Override
    public Des2NodeCharItr iterator() {
        return iterator;
    }

    @Override
    public Finder getFinder() {
        return finder;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        processor.handle(this);
    }

    @Override
    public void setMode(Des2NodeMode mode) {
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
    public Converter<Node, Triple<Node, String, Des2NodeMode>> getConverter() {
        return converter;
    }
}
