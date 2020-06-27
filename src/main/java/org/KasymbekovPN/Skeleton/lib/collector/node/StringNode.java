package org.KasymbekovPN.Skeleton.lib.collector.node;

public class StringNode extends PrimitiveNode<String> {

    public StringNode(Node parent, String value) {
        super(parent, value);
    }

    @Override
    public Node deepCopy(Node parent) {
        return new StringNode(parent, value);
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
