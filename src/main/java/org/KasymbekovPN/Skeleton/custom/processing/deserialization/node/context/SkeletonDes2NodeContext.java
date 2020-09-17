package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;

import java.util.EnumMap;

public class SkeletonDes2NodeContext implements Des2NodeContext {

    private final EnumMap<Des2NodeMode, ContextIds> contextIds;
    private final Des2NodeCharItr iterator;
    private final Finder finder;
    private final ContextProcessor processor;

    private Des2NodeMode mode = Des2NodeMode.INIT;
    private Node currentNode;
    private Node parent;

    public SkeletonDes2NodeContext(EnumMap<Des2NodeMode, ContextIds> contextIds,
                                   Des2NodeCharItr iterator,
                                   Finder finder,
                                   ContextProcessor processor) {
        this.contextIds = contextIds;
        this.iterator = iterator;
        this.finder = finder;
        this.processor = processor;
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
    public void runProcessor() {
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

    //<
//    public enum Mode {
//        INIT,
//        OBJECT,
//        ARRAY,
//        BOOLEAN,
//        CHARACTER,
//        NUMBER,
//        STRING
//    }
}
