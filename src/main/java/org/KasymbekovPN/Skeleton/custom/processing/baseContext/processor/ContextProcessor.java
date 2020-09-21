package org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ContextProcessor implements Processor<Context> {

    private static final Logger log = LoggerFactory.getLogger(ContextProcessor.class);

    private final Map<String, Task<Context>> tasks = new HashMap<>();
    private final AggregateResult processorResult;

    public ContextProcessor(AggregateResult processorResult) {
        this.processorResult = processorResult;
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
    public Result handle(Context object) {
        Iterator<String> taskIterator = object.getContextIds().taskIterator();
        while (taskIterator.hasNext()){
            String taskId = taskIterator.next();
            if (tasks.containsKey(taskId)){
                processorResult.put(taskId, tasks.get(taskId).handle(object));
            } else {
                log.warn("Processor doesn't contain task with ID '{}'", taskId);
            }
            //<
//            SimpleResult simpleResult;
//            String taskId = taskIterator.next();
//            if (tasks.containsKey(taskId)){
//                simpleResult = (SimpleResult) tasks.get(taskId).handle(object);
//            } else {
//                simpleResult = wrongSimpleResult.createNew();
//                simpleResult.setStatus(String.format(TASK_IS_NOT_EXIST, taskId));
//            }
//            processorResult.put(taskId, simpleResult);
        }

        return processorResult;
    }

    @Override
    public Result getProcessorResult() {
        return processorResult;
    }
}
