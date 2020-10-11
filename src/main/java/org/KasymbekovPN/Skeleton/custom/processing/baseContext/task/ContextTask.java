package org.KasymbekovPN.Skeleton.custom.processing.baseContext.task;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
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

public class ContextTask<T extends Context> implements Task<T> {

    private static final Class<? extends AggregateResult> AGGREGATE_RESULT_CLASS = SKAggregateResult.class;
    private static final Logger log = LoggerFactory.getLogger(ContextTask.class);

    private final String id;
    private final Map<String, TaskHandler<T>> handlers = new HashMap<>();

    private AggregateResult taskResult;

    public ContextTask(String id) {
        this.id = id;
    }

    public ContextTask(String id, AggregateResult taskResult) {
        this.id = id;
        this.taskResult = taskResult;
    }

    @Override
    public Result handle(T object) throws NoSuchMethodException,
                                          InstantiationException,
                                          IllegalAccessException,
                                          InvocationTargetException {
        checkTaskResult();
        Iterator<String> handlerIterator = object.getContextIds().handlerIterator();
        while (handlerIterator.hasNext()){
            String handlerId = handlerIterator.next();
            if (handlers.containsKey(handlerId)){
                taskResult.put(handlerId, handlers.get(handlerId).handle(object));
            } else {
                log.warn("Context task doesn't contain handler with ID '{}'", handlerId);
            }
        }

        return taskResult;
    }

    @Override
    public Task<T> add(TaskHandler<T> taskHandler) {
        String id = taskHandler.getId();
        if (handlers.containsKey(id)){
            log.warn("Handler with ID '{}' already is added", id);
        } else {
            handlers.put(id, taskHandler);
        }

        return this;
    }

    @Override
    public Result getTaskResult() throws InvocationTargetException,
                                         NoSuchMethodException,
                                         InstantiationException,
                                         IllegalAccessException {
        checkTaskResult();
        return taskResult;
    }

    @Override
    public String getId() {
        return id;
    }

    private void checkTaskResult() throws NoSuchMethodException,
                                          IllegalAccessException,
                                          InvocationTargetException,
                                          InstantiationException {
        if (taskResult == null){
            Constructor<? extends AggregateResult> constructor = AGGREGATE_RESULT_CLASS.getConstructor();
            taskResult = constructor.newInstance();
        }
    }
}
