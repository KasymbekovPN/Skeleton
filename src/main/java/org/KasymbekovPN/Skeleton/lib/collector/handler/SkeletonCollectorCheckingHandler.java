package org.KasymbekovPN.Skeleton.lib.collector.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkeletonCollectorCheckingHandler implements CollectorCheckingHandler {

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorCheckingProcess.class);

    private final Class<? extends CollectorCheckingProcess> defaultProcessType;
    private final Map<String, CollectorCheckingProcess> processes = new HashMap<>();

    public SkeletonCollectorCheckingHandler(Class<? extends CollectorCheckingProcess> defaultProcessType) {
        this.defaultProcessType = defaultProcessType;
    }

    @Override
    public boolean isExisting(String processName) {
        return processes.containsKey(processName);
    }

    @Override
    public Optional<CollectorCheckingProcess> add(String processName, CollectorCheckingProcess collectorCheckingProcess) {
        return Optional.ofNullable(processes.put(processName, collectorCheckingProcess));
    }

    @Override
    public Optional<CollectorCheckingProcess> add(String processName) {
        try {
            CollectorCheckingProcess collectorHandingProcess = defaultProcessType.getConstructor().newInstance();
            processes.put(processName, collectorHandingProcess);
            return Optional.of(collectorHandingProcess);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<CollectorCheckingProcess> remove(String processName) {
        if (processes.containsKey(processName)){
            return Optional.ofNullable(processes.remove(processName));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CollectorCheckingProcess> get(String processName) {
        if (processes.containsKey(processName)){
            return Optional.ofNullable(processes.get(processName));
        }
        return Optional.empty();
    }

    @Override
    public Map<String, CollectorCheckingResult> handle(Collector collector) {
        Map<String, CollectorCheckingResult> results = new HashMap<>();
        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
            collector.apply(entry.getValue());
            results.put(entry.getKey(), entry.getValue().getResult());
        }

        return results;
    }
}
