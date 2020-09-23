package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;

public class SkeletonDes2InstanceContext implements Des2InstanceContext {

    private final ContextIds contextIds;

    public SkeletonDes2InstanceContext(ContextIds contextIds) {
        this.contextIds = contextIds;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }
}
