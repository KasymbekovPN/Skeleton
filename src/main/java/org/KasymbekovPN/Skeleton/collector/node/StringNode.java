package org.KasymbekovPN.Skeleton.collector.node;

public class StringNode extends PrimitiveNode<String> {

    public StringNode(Node parent, String value) {
        super(parent, value);
    }

    @Override
    public String toString() {
        return "StringNode{" +
                "value='" + value + '\'' +
                '}';
    }
}
