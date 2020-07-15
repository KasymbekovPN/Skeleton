package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public class StringNode extends PrimitiveNode<String> {

    public static EntityItem ei(){
        return NodeEI.stringEI();
    }

    public StringNode(Node parent, String value) {
        super(parent, value);
    }

    @Override
    public Node deepCopy(Node parent) {
        return new StringNode(parent, value);
    }

    @Override
    public EntityItem getEI() {
        return ei();
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
