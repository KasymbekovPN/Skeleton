package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;

import java.util.Objects;

// TODO: 22.11.2020 test
public class InvalidNode implements Node {

    public static EntityItem ei(){
        return NodeEI.invalidEI();
    }

    private Node parent;
    private String status;
    private String raw;

    public InvalidNode(Node parent, String status, String raw) {
        this.parent = parent;
        this.status = status;
        this.raw = raw;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Node deepCopy(Node parent) {
        return new InvalidNode(parent, status, raw);
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
        return "InvalidNode{" +
                " status='" + status + '\'' +
                ", raw='" + raw + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidNode that = (InvalidNode) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(raw, that.raw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, raw);
    }
}
