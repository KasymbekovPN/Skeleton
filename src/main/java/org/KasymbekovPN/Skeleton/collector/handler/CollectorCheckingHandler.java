package org.KasymbekovPN.Skeleton.collector.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;

import java.util.Map;
import java.util.Optional;

public interface CollectorCheckingHandler {
    boolean isExisting(String processName);
    Optional<CollectorCheckingProcess> add(String processName, CollectorCheckingProcess collectorCheckingProcess);
    Optional<CollectorCheckingProcess> add(String processName);
    Optional<CollectorCheckingProcess> remove(String processName);
    Optional<CollectorCheckingProcess> get(String processName);
    Map<String, SkeletonCheckResult> handle(Collector collector, boolean cleanHandles);
}
