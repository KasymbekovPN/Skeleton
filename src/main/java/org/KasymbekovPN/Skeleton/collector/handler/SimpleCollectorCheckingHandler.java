package org.KasymbekovPN.Skeleton.collector.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SimpleCollectorCheckingHandler implements CollectorCheckingHandler {

    private static final Logger log = LoggerFactory.getLogger(SimpleCollectorCheckingHandler.class);

    private final Map<String, CollectorCheckingProcess> processes = new HashMap<>();

    private Map<String, SkeletonCheckResult> results = new HashMap<>();
    private boolean cleanHandlers = false;

    @Override
    public void doIt(Collector collector) {
        results.clear();
        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
            collector.doIt(entry.getValue());
            results.put(entry.getKey(), entry.getValue().getResult(cleanHandlers));
        }
    }

    @Override
    public void doIt(Collector collector, boolean cleanHandlers) {
        this.cleanHandlers = cleanHandlers;
        doIt(collector);
    }

    @Override
    public void addProcess(String key, CollectorCheckingProcess collectorCheckingProcess) {
        processes.put(key, collectorCheckingProcess);
    }

    @Override
    public Optional<CollectorCheckingProcess> getProcess(String key) {
        return processes.containsKey(key)
                ? Optional.of(processes.get(key))
                : Optional.empty();
    }

    @Override
    public SkeletonCheckResult getCheckingResult() {
        SkeletonCheckResult result = SkeletonCheckResult.NONE;
        if (results.containsValue(SkeletonCheckResult.EXCLUDE)){
            result = SkeletonCheckResult.EXCLUDE;
        } else if (results.containsValue(SkeletonCheckResult.INCLUDE)){
            result = SkeletonCheckResult.INCLUDE;
        }

        return result;
    }

    @Override
    public Map<String, SkeletonCheckResult> getResults() {
        return results;
    }
}
