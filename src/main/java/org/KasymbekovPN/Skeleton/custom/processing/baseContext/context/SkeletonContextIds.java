package org.KasymbekovPN.Skeleton.custom.processing.baseContext.context;

import java.util.*;

public class SkeletonContextIds implements MutableContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final Map<Integer, List<String>> handlerIds = new HashMap<>();

    private String currentTaskId;

    @Override
    public void addIds(String taskId, String... wrapperIds) {
        int hash = taskId.hashCode();
        if (this.handlerIds.containsKey(hash)){
            this.handlerIds.get(hash).addAll(Arrays.asList(wrapperIds));
        } else {
            this.taskIds.add(taskId);
            this.handlerIds.put(hash, Arrays.asList(wrapperIds));
        }
    }

    @Override
    public Iterator<String> taskIterator() {
        return new TaskItr();
    }

    @Override
    public Iterator<String> handlerIterator() {
        return new HandlerItr();
    }

    private class TaskItr implements Iterator<String>{

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < SkeletonContextIds.this.taskIds.size();
        }

        @Override
        public String next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            SkeletonContextIds.this.currentTaskId = SkeletonContextIds.this.taskIds.get(cursor++);

            return SkeletonContextIds.this.currentTaskId;
        }
    }

    private class HandlerItr implements Iterator<String>{

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < getHandlerIds().size();
        }

        @Override
        public String next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            return getHandlerIds().get(cursor++);
        }

        private List<String> getHandlerIds(){
            int hash = SkeletonContextIds.this.currentTaskId.hashCode();
            return SkeletonContextIds.this.handlerIds.get(hash);
        }
    }
}
