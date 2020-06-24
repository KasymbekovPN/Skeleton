package org.KasymbekovPN.Skeleton.lib.serialization.group.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public interface SerializerGroupHandler {
    void accept(EntityItem serializer, Collector collector);
}
