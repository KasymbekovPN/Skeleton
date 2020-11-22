package org.KasymbekovPN.Skeleton.lib.processing.context.ids;

import java.util.*;

// TODO: 22.11.2020 test
public class SKSimpleContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final List<String> handlerIds;

    public SKSimpleContextIds(String taskId, String... handlerIds) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKSimpleContextIds that = (SKSimpleContextIds) o;
        return Objects.equals(taskIds, that.taskIds) &&
                Objects.equals(handlerIds, that.handlerIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskIds, handlerIds);
    }
}
