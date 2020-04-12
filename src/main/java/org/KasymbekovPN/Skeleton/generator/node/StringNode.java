package org.KasymbekovPN.Skeleton.generator.node;

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
