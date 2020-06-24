package org.KasymbekovPN.Skeleton.lib.serialization.group;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupHandler;

public interface SerializerGroup {
    void handle(EntityItem serializerKey, Class<?> clazz);
    void visit(SerializerGroupHandler handler);
}
