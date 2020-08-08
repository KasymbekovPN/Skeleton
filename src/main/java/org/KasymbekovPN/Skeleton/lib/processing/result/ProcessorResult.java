package org.KasymbekovPN.Skeleton.lib.processing.result;

//< how reset result
public interface ProcessorResult extends Result{
    void put(String taskId, TaskResult taskResult);
}
