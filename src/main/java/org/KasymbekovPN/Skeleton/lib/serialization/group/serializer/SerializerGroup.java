package org.KasymbekovPN.Skeleton.lib.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;

public interface SerializerGroup {
    void handle(EntityItem serializerKey, Class<?> clazz);
    void accept(SerializerGroupVisitor visitor);
}
