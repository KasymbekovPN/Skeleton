package org.KasymbekovPN.Skeleton.collector.node;

public class NumberNode extends PrimitiveNode<Number> {

    public NumberNode(Node parent, Number value) {
        super(parent, value);
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "value=" + value +
                '}';
    }
}
