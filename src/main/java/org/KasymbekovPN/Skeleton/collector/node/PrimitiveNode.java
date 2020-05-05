package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;

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
    public void doIt(CollectorHandingProcess collectorHandingProcess) {
        collectorHandingProcess.doIt(this);
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }
}
