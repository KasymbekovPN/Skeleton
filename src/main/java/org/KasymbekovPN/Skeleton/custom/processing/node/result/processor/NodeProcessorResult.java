package org.KasymbekovPN.Skeleton.custom.processing.node.result.processor;

import org.KasymbekovPN.Skeleton.lib.processing.result.ProcessorResult;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;

import java.util.HashMap;
import java.util.Map;

public class NodeProcessorResult implements ProcessorResult {

    private final Map<String, TaskResult> results = new HashMap<>();

    @Override
    public void put(String taskId, TaskResult taskResult) {
        results.put(taskId, taskResult);
    }

    @Override
    public void reset() {
        results.clear();
    }
}
