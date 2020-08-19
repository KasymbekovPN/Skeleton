package org.KasymbekovPN.Skeleton.lib.collector.handler;

//<
//import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.lib.filter.Filter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//
//public class SkeletonCollectorCheckingHandler implements CollectorCheckingHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(SkeletonCollectorCheckingProcess.class);
//
//    private final Class<? extends CollectorCheckingProcess> defaultProcessType;
//    private final Map<String, CollectorCheckingProcess> processes = new HashMap<>();
//
//    public SkeletonCollectorCheckingHandler(Class<? extends CollectorCheckingProcess> defaultProcessType) {
//        this.defaultProcessType = defaultProcessType;
//    }
//
//    @Override
//    public boolean isExisting(String processName) {
//        return processes.containsKey(processName);
//    }
//
//    @Override
//    public Optional<CollectorCheckingProcess> add(String processName, CollectorCheckingProcess collectorCheckingProcess) {
//        return Optional.ofNullable(processes.put(processName, collectorCheckingProcess));
//    }
//
//    @Override
//    public Optional<CollectorCheckingProcess> add(String processName) {
//        try {
//            CollectorCheckingProcess collectorHandingProcess = defaultProcessType.getConstructor().newInstance();
//            processes.put(processName, collectorHandingProcess);
//            return Optional.of(collectorHandingProcess);
//        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<CollectorCheckingProcess> remove(String processName) {
//        if (processes.containsKey(processName)){
//            return Optional.ofNullable(processes.remove(processName));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<CollectorCheckingProcess> get(String processName) {
//        if (processes.containsKey(processName)){
//            return Optional.ofNullable(processes.get(processName));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Map<String, CollectorCheckingResult> handle(Collector collector) {
//        Map<String, CollectorCheckingResult> results = new HashMap<>();
//        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
//            collector.apply(entry.getValue());
//            results.put(entry.getKey(), entry.getValue().getResult());
//        }
//
//        return results;
//    }
//
//    @Override
//    public Map<String, CollectorCheckingResult> handle(Collector collector, Filter<String> processIdFilter) {
//        Map<String, CollectorCheckingResult> results = new HashMap<>();
//        Deque<String> filteredProcessIds = processIdFilter.filter(new ArrayDeque<>(processes.keySet()));
//        for (String filteredProcessId : filteredProcessIds) {
//            CollectorCheckingProcess process = processes.get(filteredProcessId);
//            collector.apply(process);
//            results.put(filteredProcessId, process.getResult());
//        }
//
//        return results;
//    }
//
//    @Override
//    public Map<String, CollectorCheckingResult> handle(Node node) {
//        Map<String, CollectorCheckingResult> results = new HashMap<>();
//        for (Map.Entry<String, CollectorCheckingProcess> entry : processes.entrySet()) {
//            node.apply(entry.getValue());
//            results.put(entry.getKey(), entry.getValue().getResult());
//        }
//
//        return results;
//    }
//
//    @Override
//    public Map<String, CollectorCheckingResult> handle(Node node, Filter<String> processIdFilter) {
//        Map<String, CollectorCheckingResult> results = new HashMap<>();
//        Deque<String> filteredProcessIds = processIdFilter.filter(new ArrayDeque<>(processes.keySet()));
//        for (String filteredProcessId : filteredProcessIds) {
//            CollectorCheckingProcess process = processes.get(filteredProcessId);
//            node.apply(process);
//            results.put(filteredProcessId, process.getResult());
//        }
//
//        return results;
//    }
//
//    @Override
//    public CollectorCheckingResult handle(String processId, Collector collector) {
//        CollectorCheckingResult result = CollectorCheckingResult.NONE;
//        if (processes.containsKey(processId)){
//            collector.apply(processes.get(processId));
//            result = processes.get(processId).getResult();
//        }
//
//        return result;
//    }
//
//    @Override
//    public CollectorCheckingResult handle(String processId, Node node) {
//        CollectorCheckingResult result = CollectorCheckingResult.NONE;
//        if (processes.containsKey(processId)){
//            node.apply(processes.get(processId));
//            result = processes.get(processId).getResult();
//        }
//
//        return result;
//    }
//}
