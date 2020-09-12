package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;

import java.util.*;

public class NodeWritingContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> wrapperIds;

    public NodeWritingContextIds(String taskId, String... wrapperIds) {
        this.taskIds.add(taskId);
        this.wrapperIds = Arrays.asList(wrapperIds);
    }

    @Override
    public Iterator<String> taskIterator() {
        return taskIds.iterator();
    }

    @Override
    public Iterator<String> wrapperIterator() {
        return wrapperIds.iterator();
    }
}
