package org.KasymbekovPN.Skeleton.generator.node;

public class CharacterNode extends PrimitiveNode<Character> {

    public CharacterNode(Node parent, Character value) {
        super(parent, value);
    }

    @Override
    public String toString() {
        return "CharacterNode{" +
                "value=" + value +
                '}';
    }
}
