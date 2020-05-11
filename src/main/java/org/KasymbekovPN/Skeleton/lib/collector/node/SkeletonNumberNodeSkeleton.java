package org.KasymbekovPN.Skeleton.lib.collector.node;

public class SkeletonNumberNodeSkeleton extends SkeletonPrimitiveNode<Number> {

    public SkeletonNumberNodeSkeleton(Node parent, Number value) {
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
