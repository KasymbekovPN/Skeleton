package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

import java.util.Objects;

public abstract class PrimitiveNode<T> implements Node {

    private Node parent;
    protected T value;

    public T getValue() {
        return value;
    }

    public PrimitiveNode(Node parent, T value) {
        this.parent = parent;
        this.value = value;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public boolean is(EntityItem ei) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveNode<?> that = (PrimitiveNode<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
