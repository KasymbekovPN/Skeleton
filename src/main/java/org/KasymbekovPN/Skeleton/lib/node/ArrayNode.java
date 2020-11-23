package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArrayNode implements Node {

    private Node parent;
    private List<Node> children = new ArrayList<>();

    public static EntityItem ei(){
        return NodeEI.arrayEI();
    }

    public List<Node> getChildren() {
        return children;
    }

    public ArrayNode(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Node deepCopy(Node parent) {
        ArrayNode arrayNode = new ArrayNode(parent);
        for (Node child : children) {
            arrayNode.children.add(child.deepCopy(arrayNode));
        }

        return arrayNode;
    }

    @Override
    public EntityItem getEI() {
        return ei();
    }

    @Override
    public Optional<Node> addChild(Node value) {
        if (value == null){
            return Optional.empty();
        }

        children.add(value);
        return Optional.of(value);
    }

    @Override
    public boolean is(EntityItem ei) {
        return ei().equals(ei);
    }

    @Override
    public String toString() {
        return "ArrayNode{ " + children + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayNode arrayNode = (ArrayNode) o;
        return Objects.equals(children, arrayNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }
}
