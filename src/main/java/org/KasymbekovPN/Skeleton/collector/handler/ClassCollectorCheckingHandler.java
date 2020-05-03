package org.KasymbekovPN.Skeleton.collector.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassCollectorCheckingHandler implements CollectorCheckingHandler {

    private static final Logger log = LoggerFactory.getLogger(ClassCollectorCheckingHandler.class);

    private final Map<String, CollectorCheckingProcess> processes = new HashMap<>();

    private Set<SkeletonCheckResult> results = new HashSet<>();

    @Override
    public void doIt(Collector collector) {
        results.clear();
        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
            collector.doIt(entry.getValue());
        }
    }

    @Override
    public void addProcess(CollectorCheckingProcess collectorCheckingProcess) {
        processes.put(collectorCheckingProcess.getClass().getName(), collectorCheckingProcess);
    }

    @Override
    public SkeletonCheckResult getCheckingResult() {
        Set<SkeletonCheckResult> results = new HashSet<>();
        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
            results.add(entry.getValue().getResult());
        }

        SkeletonCheckResult result = SkeletonCheckResult.NONE;
        if (results.contains(SkeletonCheckResult.EXCLUDE)){
            result = SkeletonCheckResult.EXCLUDE;
        } else if (results.contains(SkeletonCheckResult.INCLUDE)){
            result = SkeletonCheckResult.INCLUDE;
        }

        return result;
    }

    //<
//    @Override
//    public void addProcess(CollectorHandingProcess collectorHandingProcess) {
//        //<
//        processes.put((Class<? extends CollectorCheckingProcess>) collectorHandingProcess.getClass(), (CollectorCheckingProcess) collectorHandingProcess);
//    }
}
