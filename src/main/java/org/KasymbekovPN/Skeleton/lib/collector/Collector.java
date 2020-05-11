package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;

import java.util.List;
import java.util.Optional;

public interface Collector {
    void clear();
    void reset();

    void beginObject(String property);
    void beginObject();

    void addProperty(String property, String value);
    void addProperty(String property, Number value);
    void addProperty(String property, Boolean value);
    void addProperty(String property, Character value);

    void beginArray(String property);
    void beginArray();

    void addProperty(String value);
    void addProperty(Number value);
    void addProperty(Boolean value);
    void addProperty(Character value);

    void end();

    void apply(CollectorProcess collectorProcess);

    void setTarget(List<String> path);

    CollectorStructure getCollectorStructure();

    Optional<Node> getNodeByPath(Node node, List<String> path, Class<? extends Node> clazz);
}
