package org.KasymbekovPN.Skeleton.custom.processing.baseContext.task;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextTask implements Task<Context> {

    private static final String WRAPPER_IS_NOT_EXIST = "Wrapper '%s' isn't exist";

    private final Map<String, TaskWrapper<Context>> wrappers = new HashMap<>();
    private final AggregateResult taskResult;

    private Result wrongResult;

    public ContextTask(AggregateResult taskResult, Result wrongResult) {
        this.taskResult = taskResult;
        this.wrongResult = wrongResult;
    }

    @Override
    public AggregateResult handle(Context object) {
        List<String> wrapperIds = object.getWrapperIds();
        for (String wrapperId : wrapperIds) {
            taskResult.put(
                    wrapperId,
                    wrappers.containsKey(wrapperId)
                            ? wrappers.get(wrapperId).handle(object)
                            : getWrongResult(String.format(WRAPPER_IS_NOT_EXIST, wrapperId))
            );
        }

        return taskResult;
    }

    @Override
    public Task<Context> add(String wrapperId, TaskWrapper<Context> taskWrapper) {
        wrappers.put(wrapperId, taskWrapper);
        return this;
    }

    @Override
    public Result getResult(String wrapperId) {
        return taskResult.get(wrapperId);
    }

    private Result getWrongResult(String status){
        Result newWrongResult = wrongResult.createNew();
        newWrongResult.setStatus(status);

        return newWrongResult;
    }
}
