package org.KasymbekovPN.Skeleton.collector.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;

import java.util.Map;
import java.util.Optional;

public interface CollectorCheckingHandler {
    void doIt(Collector collector);
    void doIt(Collector collector, boolean cleanHandlers);
    void addProcess(String key, CollectorCheckingProcess collectorCheckingProcess);
    Optional<CollectorCheckingProcess> getProcess(String key);
    SkeletonCheckResult getCheckingResult();
    Map<String, SkeletonCheckResult> getResults();
}
