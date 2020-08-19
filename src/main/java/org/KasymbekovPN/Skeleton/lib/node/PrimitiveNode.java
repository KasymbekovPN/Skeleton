package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

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
    public void apply(Task<Node> task) {
        task.handle(this);
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
