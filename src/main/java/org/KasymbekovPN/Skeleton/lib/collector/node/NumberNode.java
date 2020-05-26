package org.KasymbekovPN.Skeleton.lib.collector.node;

public class NumberNode extends PrimitiveNode<Number> {

    public NumberNode(Node parent, Number value) {
        super(parent, value);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "value=" + value +
                '}';
    }
}
