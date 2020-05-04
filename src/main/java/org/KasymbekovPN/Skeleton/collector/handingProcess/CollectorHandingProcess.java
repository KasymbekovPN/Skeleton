package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;

public interface CollectorHandingProcess {
    void doIt(Node node);
    void addHandler(Class<? extends Node> clazz, CollectorHandlingProcessHandler collectorHandlingProcessHandler);
}
