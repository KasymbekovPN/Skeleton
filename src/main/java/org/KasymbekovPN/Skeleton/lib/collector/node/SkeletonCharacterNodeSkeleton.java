package org.KasymbekovPN.Skeleton.lib.collector.node;

public class SkeletonCharacterNodeSkeleton extends SkeletonPrimitiveNode<Character> {

    public SkeletonCharacterNodeSkeleton(Node parent, Character value) {
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
