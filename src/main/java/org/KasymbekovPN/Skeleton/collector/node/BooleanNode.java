package org.KasymbekovPN.Skeleton.collector.node;

public class BooleanNode extends PrimitiveNode<Boolean> {

    public BooleanNode(Node parent, Boolean value) {
        super(parent, value);
    }

    @Override
    public String toString() {
        return "BooleanNode{" +
                "value=" + value +
                '}';
    }
}
