package org.KasymbekovPN.Skeleton.lib.collector.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;

import java.util.Map;
import java.util.Optional;

public interface CollectorCheckingHandler {
    boolean isExisting(String processName);
    Optional<CollectorCheckingProcess> add(String processName, CollectorCheckingProcess collectorCheckingProcess);
    Optional<CollectorCheckingProcess> add(String processName);
    Optional<CollectorCheckingProcess> remove(String processName);
    Optional<CollectorCheckingProcess> get(String processName);
    Map<String, CollectorCheckingResult> handle(Collector collector);
    Map<String, CollectorCheckingResult> handle(Node node);
    CollectorCheckingResult handle(String processId, Collector collector);
    CollectorCheckingResult handle(String processId, Node node);
}
