package org.KasymbekovPN.Skeleton.custom.processing.node.processor;

import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.result.ProcessorResult;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

import java.util.*;

public class NodeProcessor implements Processor<Node> {

    private final Map<String, Task<Node>> tasks = new HashMap<>();
    private final ProcessorResult processorResult;

    public NodeProcessor(ProcessorResult processorResult) {
        this.processorResult = processorResult;
    }

    @Override
    public Task<Node> add(String taskId, Task<Node> task) {
        return tasks.put(taskId, task);
    }

    @Override
    public Optional<Task<Node>> get(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.get(taskId))
                : Optional.empty();
    }

    @Override
    public Optional<Task<Node>> remove(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.remove(taskId))
                : Optional.empty();
    }

    @Override
    public ProcessorResult handle(Node object, Filter<String> taskIdFilter) {
        Deque<String> filterKeys = taskIdFilter.filter(new ArrayDeque<>(tasks.keySet()));
        for (String filterKey : filterKeys) {
            TaskResult taskResult = tasks.get(filterKey).handle(object);
            processorResult.put(filterKey, taskResult);
        }

        return processorResult;
    }

    @Override
    public ProcessorResult handle(Node object) {
        for (Map.Entry<String, Task<Node>> entry : tasks.entrySet()) {
            String taskId = entry.getKey();
            TaskResult taskResult = tasks.get(taskId).handle(object);
            processorResult.put(taskId, taskResult);
        }

        return processorResult;
    }

    @Override
    public ProcessorResult getResult() {
        return processorResult;
    }
}
