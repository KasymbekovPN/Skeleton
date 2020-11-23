package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

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
    public boolean is(EntityItem ei) {
        return ei().equals(ei);
    }

    @Override
    public String toString() {
        return "StringNode{ \"" + value + "\" }";
    }
}
