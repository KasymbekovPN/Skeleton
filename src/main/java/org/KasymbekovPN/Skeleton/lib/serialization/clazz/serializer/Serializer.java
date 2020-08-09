package org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;

public interface Serializer {
    void serialize(Class<?> clazz);
    //< del apply
    void apply(CollectorProcess collectorProcess);
    //<
    void clear();
    void setCollector(Collector collector);
    Collector getCollector();
}
