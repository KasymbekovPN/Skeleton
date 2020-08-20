package org.KasymbekovPN.Skeleton.lib.collector.path;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SkeletonCollectorPath implements CollectorPath {

    private List<String> path;
    private EntityItem ei;

    public SkeletonCollectorPath(List<String> path, EntityItem ei) {
        this.path = path;
        this.ei = ei;
    }

    @Override
    public void setPath(List<String> path) {
        this.path = path;
    }

    @Override
    public void setEi(EntityItem ei) {
        this.ei = ei;
    }

    @Override
    public List<String> getPath() {
        return path;
    }

    @Override
    public EntityItem getEi() {
        return ei;
    }

    @Override
    public Iterator<Pair<String, EntityItem>> iterator() {
        return new SkeletonCollectorPath.Itr();
    }

    private class Itr implements Iterator<Pair<String, EntityItem>>{

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < SkeletonCollectorPath.this.path.size();
        }

        @Override
        public Pair<String, EntityItem> next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            int i = cursor++;

            EntityItem ei = i == SkeletonCollectorPath.this.path.size() - 1
                    ? SkeletonCollectorPath.this.ei
                    : ObjectNode.ei();

            return new MutablePair<>(SkeletonCollectorPath.this.path.get(i), ei);
        }
    }
}
