package org.KasymbekovPN.Skeleton.lib.collector.process;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

public interface CollectorProcess {
    void handle(Node node);
    void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler);
}
