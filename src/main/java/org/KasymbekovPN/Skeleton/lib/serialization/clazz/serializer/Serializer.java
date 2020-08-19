package org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;

public interface Serializer {
    void serialize(Class<?> clazz);
    Collector getCollector();
    Collector attachCollector(Collector collector);
    String getId();
}
