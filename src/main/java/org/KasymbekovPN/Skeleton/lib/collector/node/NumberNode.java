package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

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
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "value=" + value +
                '}';
    }
}
