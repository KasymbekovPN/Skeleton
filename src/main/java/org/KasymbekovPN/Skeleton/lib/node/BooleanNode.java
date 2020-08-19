package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

public class BooleanNode extends PrimitiveNode<Boolean> {

    public static EntityItem ei(){
        return NodeEI.booleanEI();
    }

    public BooleanNode(Node parent, Boolean value) {
        super(parent, value);
    }

    @Override
    public Node deepCopy(Node parent) {
        return new BooleanNode(parent, value);
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
        return "BooleanNode{ " + value + " }";
    }
}
