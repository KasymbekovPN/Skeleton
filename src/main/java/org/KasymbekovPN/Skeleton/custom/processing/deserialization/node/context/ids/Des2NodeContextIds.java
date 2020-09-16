package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.ids;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//< !!! make simple contextIds
public class Des2NodeContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> wrapperIds;

    public Des2NodeContextIds(String taskId, String... wrapperIds) {
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
