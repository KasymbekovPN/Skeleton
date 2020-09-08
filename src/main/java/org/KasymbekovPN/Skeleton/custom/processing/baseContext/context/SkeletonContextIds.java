package org.KasymbekovPN.Skeleton.custom.processing.baseContext.context;

import java.util.*;

public class SkeletonContextIds implements ContextIds {

    private final List<String> taskIds = new ArrayList<>();
    private final Map<Integer, List<String>> wrapperIds = new HashMap<>();

    private String currentTaskId;

    @Override
    public void addIds(String taskId, String... wrapperIds) {
        int hash = taskId.hashCode();
        if (this.wrapperIds.containsKey(hash)){
            this.wrapperIds.get(hash).addAll(Arrays.asList(wrapperIds));
        } else {
            this.taskIds.add(taskId);
            this.wrapperIds.put(hash, Arrays.asList(wrapperIds));
        }
    }

    @Override
    public Iterator<String> taskIterator() {
        return new TaskItr();
    }

    @Override
    public Iterator<String> wrapperIterator() {
        return new WrapperItr();
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

    private class WrapperItr implements Iterator<String>{

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < getWrapperIds().size();
        }

        @Override
        public String next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            return getWrapperIds().get(cursor++);
        }

        private List<String> getWrapperIds(){
            int hash = SkeletonContextIds.this.currentTaskId.hashCode();
            return SkeletonContextIds.this.wrapperIds.get(hash);
        }
    }
}
