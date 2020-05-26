package org.KasymbekovPN.Skeleton.lib.collector.node;

public class BooleanNode extends PrimitiveNode<Boolean> {

    public BooleanNode(Node parent, Boolean value) {
        super(parent, value);
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public String toString() {
        return "BooleanNode{" +
                "value=" + value +
                '}';
    }
}
