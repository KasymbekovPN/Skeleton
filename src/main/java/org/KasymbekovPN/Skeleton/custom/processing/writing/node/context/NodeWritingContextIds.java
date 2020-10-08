package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;

import java.util.*;

//< make simple contextIds
public class NodeWritingContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> handlerIds;

    public NodeWritingContextIds(String taskId, String... handlerIds) {
        this.taskIds.add(taskId);
        this.handlerIds = Arrays.asList(handlerIds);
    }

    @Override
    public Iterator<String> taskIterator() {
        return taskIds.iterator();
    }

    @Override
    public Iterator<String> handlerIterator() {
        return handlerIds.iterator();
    }
}
