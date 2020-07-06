package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public class SkeletonNodeSerializedDataWrapper implements NodeSerializedDataWrapper {

    //< !!! add type checking for EntityType instance
    private final EntityItem entityItem;
    private final String data;

    public SkeletonNodeSerializedDataWrapper(EntityItem entityItem, String data) {
        this.entityItem = entityItem;
        this.data = data;
    }

    @Override
    public EntityItem getType() {
        return entityItem;
    }

    @Override
    public String getData() {
        return data;
    }
}
