package org.KasymbekovPN.Skeleton.lib.collector.node;

public class SkeletonStringNode extends SkeletonPrimitiveNode<String> {

    public SkeletonStringNode(Node parent, String value) {
        super(parent, value);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String toString() {
        return "StringNode{" +
                "value='" + value + '\'' +
                '}';
    }
}
