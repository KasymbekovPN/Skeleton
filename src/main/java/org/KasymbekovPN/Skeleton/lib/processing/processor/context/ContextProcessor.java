package org.KasymbekovPN.Skeleton.lib.processing.processor.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.result.SKAggregateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ContextProcessor<T extends Context<? extends ContextStateMemento>> implements Processor<T> {
    private static final Class<? extends AggregateResult> AGGREGATE_RESULT_CLASS = SKAggregateResult.class;
    private static final Logger log = LoggerFactory.getLogger(ContextProcessor.class);

    private final Map<String, Task<T>> tasks = new HashMap<>();
    private AggregateResult processorResult;

    public ContextProcessor() {
    }

    public ContextProcessor(AggregateResult processorResult) {
        this.processorResult = processorResult;
    }

    @Override
    public ContextProcessor<T> add(Task<T> task) {
        String id = task.getId();
        if (tasks.containsKey(id)){
            log.warn("Task with ID '{}' already is added", id);
        } else {
            tasks.put(id, task);
        }

        return this;
    }

    @Override
    public Optional<Task<T>> get(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.get(taskId))
                : Optional.empty();
    }

    @Override
    public Optional<Task<T>> remove(String taskId) {
        return tasks.containsKey(taskId)
                ? Optional.of(tasks.remove(taskId))
                : Optional.empty();
    }

    @Override
    public Result handle(T object) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {
        checkAggregateResult();
        Iterator<String> taskIterator = object.getContextIds().taskIterator();
        while (taskIterator.hasNext()){
            String taskId = taskIterator.next();
            if (tasks.containsKey(taskId)){
                processorResult.put(taskId, tasks.get(taskId).handle(object));
            } else {
                log.warn("Processor doesn't contain task with ID '{}'", taskId);
            }
        }

        return processorResult;
    }

    @Override
    public Result getProcessorResult() throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        checkAggregateResult();
        return processorResult;
    }

    private void checkAggregateResult() throws IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchMethodException {
        if (processorResult == null){
            Constructor<? extends AggregateResult> constructor = AGGREGATE_RESULT_CLASS.getConstructor();
            processorResult = constructor.newInstance();
        }
    }
}
