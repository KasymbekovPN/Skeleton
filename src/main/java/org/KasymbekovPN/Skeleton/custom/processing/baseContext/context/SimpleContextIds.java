package org.KasymbekovPN.Skeleton.custom.processing.baseContext.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SimpleContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> handlerIds;

    public SimpleContextIds(String taskId, String... handlerIds) {
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
