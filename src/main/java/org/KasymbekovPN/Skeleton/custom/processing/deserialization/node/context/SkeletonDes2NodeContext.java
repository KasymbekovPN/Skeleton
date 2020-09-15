package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.node.Node;

import java.util.EnumMap;

public class SkeletonDes2NodeContext implements Des2NodeContext {

    private final EnumMap<Mode, ContextIds> contextIds;

    private Mode mode = Mode.INIT;
    private Node currentNode;

    public SkeletonDes2NodeContext(EnumMap<Mode, ContextIds> contextIds) {
        this.contextIds = contextIds;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds.get(mode);
    }

    private enum Mode {
        INIT,
        OBJECT,
        ARRAY,
        BOOLEAN,
        CHARACTER,
        NUMBER,
        STRING
    }
}
