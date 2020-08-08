package org.KasymbekovPN.Skeleton.lib.processing.processor;

import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.processing.result.ProcessorResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

import java.util.Optional;

//< !!! CollectorCheckingHandler
public interface Processor<T> {
    Task<T> add(String taskId, Task<T> task);
    Optional<Task<T>> get(String taskId);
    Optional<Task<T>> remove(String taskId);
    ProcessorResult handle(T object, Filter<String> taskIdFilter);
    ProcessorResult handle(T object);
    ProcessorResult getResult();

    //<
//    public interface CollectorCheckingHandler {
//        boolean isExisting(String processName);
//        Optional<CollectorCheckingProcess> add(String processName, CollectorCheckingProcess collectorCheckingProcess);
//        Optional<CollectorCheckingProcess> add(String processName);
//        Optional<CollectorCheckingProcess> remove(String processName);
//        Optional<CollectorCheckingProcess> get(String processName);
//        Map<String, CollectorCheckingResult> handle(Collector collector);
//        Map<String, CollectorCheckingResult> handle(Collector collector, Filter<String> processIdFilter);
//        Map<String, CollectorCheckingResult> handle(Node node);
//        Map<String, CollectorCheckingResult> handle(Node node, Filter<String> processIdFilter);
//        CollectorCheckingResult handle(String processId, Collector collector);
//        CollectorCheckingResult handle(String processId, Node node);
//    }
}
