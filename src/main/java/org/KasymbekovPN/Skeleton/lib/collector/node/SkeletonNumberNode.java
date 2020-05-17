package org.KasymbekovPN.Skeleton.lib.collector.node;

public class SkeletonNumberNode extends SkeletonPrimitiveNode<Number> {

    public SkeletonNumberNode(Node parent, Number value) {
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
