package org.KasymbekovPN.Skeleton.lib.serialization.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;

public interface Serializer {
    void serialize(Class<?> clazz);
    void apply(CollectorProcess collectorProcess);
}
