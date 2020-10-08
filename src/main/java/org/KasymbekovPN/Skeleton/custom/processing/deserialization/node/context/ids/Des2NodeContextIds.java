package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.ids;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//< !!! make simple contextIds
public class Des2NodeContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> handlerIds;

    public Des2NodeContextIds(String taskId, String... handlerIds) {
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
