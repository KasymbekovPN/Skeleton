package org.KasymbekovPN.Skeleton.collector.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;

public interface CollectorCheckingHandler {
    void doIt(Collector collector);
    void addProcess(CollectorCheckingProcess collectorCheckingProcess);
    SkeletonCheckResult getCheckingResult();
}
