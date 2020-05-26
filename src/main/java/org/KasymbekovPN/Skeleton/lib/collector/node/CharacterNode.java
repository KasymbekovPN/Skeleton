package org.KasymbekovPN.Skeleton.lib.collector.node;

public class CharacterNode extends PrimitiveNode<Character> {

    public CharacterNode(Node parent, Character value) {
        super(parent, value);
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
