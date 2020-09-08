package org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ContextProcessor implements Processor<Context> {

    private static final String TASK_IS_NOT_EXIST = "Task '%s' isn't exist";

    private final Map<String, Task<Context>> tasks = new HashMap<>();
    private final AggregateResult processorResult;

    private Result wrongResult;

    public ContextProcessor(AggregateResult processorResult,
                            Result wrongResult) {
        this.processorResult = processorResult;
        this.wrongResult = wrongResult;
    }

    @Override
    public Task<Context> add(String taskId, Task<Context> task) {
        return tasks.put(taskId, task);
    }

    @Override
    public Optional<Task<Context>> get(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.get(taskId))
                : Optional.empty();
    }

    @Override
    public Optional<Task<Context>> remove(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.remove(taskId))
                : Optional.empty();
    }

    @Override
    public AggregateResult handle(Context object) {
        Iterator<String> taskIterator = object.getContextIds().taskIterator();
        while (taskIterator.hasNext()){
            Result result;
            String taskId = taskIterator.next();
            if (tasks.containsKey(taskId)){
                result = (Result) tasks.get(taskId).handle(object);
            } else {
                result = wrongResult.createNew();
                result.setStatus(String.format(TASK_IS_NOT_EXIST, taskId));
            }

            processorResult.put(taskId, result);
        }

        return processorResult;
    }

    @Override
    public AggregateResult getResult() {
        return processorResult;
    }
}
