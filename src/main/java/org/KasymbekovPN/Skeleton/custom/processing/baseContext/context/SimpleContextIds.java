package org.KasymbekovPN.Skeleton.custom.processing.baseContext.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SimpleContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> wrapperIds;

    public SimpleContextIds(String taskId, String... wrapperIds) {
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
