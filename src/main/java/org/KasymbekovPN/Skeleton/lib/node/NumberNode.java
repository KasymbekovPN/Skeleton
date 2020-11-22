package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

// TODO: 22.11.2020 test
public class NumberNode extends PrimitiveNode<Number> {

    public static EntityItem ei(){
        return NodeEI.numberEI();
    }

    public NumberNode(Node parent, Number value) {
        super(parent, value);
    }

    @Override
    public Node deepCopy(Node parent) {
        return new NumberNode(parent, value);
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
        return "NumberNode{ " + value + " }";
    }
}
