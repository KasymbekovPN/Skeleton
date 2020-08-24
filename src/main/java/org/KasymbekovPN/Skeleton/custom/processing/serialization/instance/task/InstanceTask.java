package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.task;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceData;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskWrapper;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceTask implements Task<InstanceData> {

    private static final String WRAPPER_IS_NOT_EXIST = "Wrapper '%s' isn't exist";

    private final Map<String, TaskWrapper<InstanceData>> wrappers = new HashMap<>();
    private final AggregateResult taskResult;

    private Result wrongResult;

    public InstanceTask(AggregateResult taskResult, Result wrongResult) {
        this.taskResult = taskResult;
        this.wrongResult = wrongResult;
    }

    @Override
    public AggregateResult handle(InstanceData object) {

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
    public Task<InstanceData> add(String wrapperId, TaskWrapper<InstanceData> taskWrapper) {
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
