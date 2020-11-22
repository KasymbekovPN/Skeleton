package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

// TODO: 22.11.2020 test
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
    public boolean is(EntityItem ei) {
        return ei().equals(ei);
    }

    @Override
    public String toString() {
        return "CharacterNode{ " + value + " }";
    }
}
