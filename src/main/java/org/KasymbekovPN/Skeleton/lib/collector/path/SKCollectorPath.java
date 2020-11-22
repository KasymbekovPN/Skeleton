package org.KasymbekovPN.Skeleton.lib.collector.path;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

// TODO: 22.11.2020 test
public class SKCollectorPath implements CollectorPath {

    private List<String> path;
    private EntityItem ei;

    public SKCollectorPath(List<String> path, EntityItem ei) {
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
        return new SKCollectorPath.Itr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKCollectorPath that = (SKCollectorPath) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(ei, that.ei);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, ei);
    }

    private class Itr implements Iterator<Pair<String, EntityItem>>{

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < SKCollectorPath.this.path.size();
        }

        @Override
        public Pair<String, EntityItem> next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            int i = cursor++;

            EntityItem ei = i == SKCollectorPath.this.path.size() - 1
                    ? SKCollectorPath.this.ei
                    : ObjectNode.ei();

            return new MutablePair<>(SKCollectorPath.this.path.get(i), ei);
        }
    }
}
