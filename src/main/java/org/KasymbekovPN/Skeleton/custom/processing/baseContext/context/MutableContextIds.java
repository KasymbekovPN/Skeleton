package org.KasymbekovPN.Skeleton.custom.processing.baseContext.context;

public interface MutableContextIds extends ContextIds {
    void addIds(String taskId, String... wrapperIds);
}
