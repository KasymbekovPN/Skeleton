package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;

import java.util.EnumMap;

public class SkeletonDes2NodeContext implements Des2NodeContext {

    private final EnumMap<Mode, ContextIds> contextIds;
    private final Des2NodeCharItr iterator;

    private Mode mode = Mode.INIT;
    private Node currentNode;

    public SkeletonDes2NodeContext(EnumMap<Mode, ContextIds> contextIds,
                                   Des2NodeCharItr iterator) {
        this.contextIds = contextIds;
        this.iterator = iterator;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds.get(mode);
    }

    @Override
    public Des2NodeCharItr iterator() {
        return iterator;
    }

    public enum Mode {
        INIT,
        OBJECT,
        ARRAY,
        BOOLEAN,
        CHARACTER,
        NUMBER,
        STRING
    }
}
