package org.KasymbekovPN.Skeleton.lib.collector.node;

public class SkeletonBooleanNodeSkeleton extends SkeletonPrimitiveNode<Boolean> {

    public SkeletonBooleanNodeSkeleton(Node parent, Boolean value) {
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
