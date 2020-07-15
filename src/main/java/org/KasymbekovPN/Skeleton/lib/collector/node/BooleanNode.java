package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

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
