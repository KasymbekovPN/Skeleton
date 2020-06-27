package org.KasymbekovPN.Skeleton.lib.serialization.group;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;

public interface SerializerGroup {
    void handle(EntityItem serializerKey, Class<?> clazz);
    void accept(SerializerGroupVisitor visitor);
}
