package org.KasymbekovPN.Skeleton.custom.processing.baseContext.task;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContextTask implements Task<Context> {

    private static final Logger log = LoggerFactory.getLogger(ContextTask.class);

    private final Map<String, TaskWrapper<Context>> wrappers = new HashMap<>();
    private final AggregateResult taskResult;

    public ContextTask(AggregateResult taskResult) {
        this.taskResult = taskResult;
    }

    @Override
    public Result handle(Context object) {
        Iterator<String> wrapperIterator = object.getContextIds().wrapperIterator();
        while (wrapperIterator.hasNext()){
            String wrapperId = wrapperIterator.next();
            if (wrappers.containsKey(wrapperId)){
                taskResult.put(wrapperId, wrappers.get(wrapperId).handle(object));
            } else {
                log.warn("Context task doesn't contain wrapper with ID '{}'", wrapperId);
            }
        }

        return taskResult;
    }

    @Override
    public Task<Context> add(String wrapperId, TaskWrapper<Context> taskWrapper) {
        wrappers.put(wrapperId, taskWrapper);
        return this;
    }

    @Override
    public Result getTaskResult() {
        return taskResult;
    }
}
