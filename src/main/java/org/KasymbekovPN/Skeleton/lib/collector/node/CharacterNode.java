package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public class CharacterNode extends PrimitiveNode<Character> {

    public static EntityItem ei(){
        return NodeEI.characterEI();
    }

    public CharacterNode(Node parent, Character value) {
        super(parent, value);
    }

    @Override
    public Node deepCopy(Node parent) {
        return new CharacterNode(parent, value);
    }

    @Override
    public EntityItem getEI() {
        return ei();
    }

    @Override
    public boolean isCharacter() {
        return true;
    }

    @Override
    public String toString() {
        return "CharacterNode{" +
                "value=" + value +
                '}';
    }
}
