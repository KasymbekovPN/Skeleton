package org.KasymbekovPN.Skeleton.generator.node;

import org.KasymbekovPN.Skeleton.generator.writer.Writer;

import java.util.HashMap;
import java.util.Map;

public class ObjectNode implements Node {

    private Node parent;
    private Map<String, Node> children = new HashMap<>();

    public Map<String, Node> getChildren() {
        return children;
    }

    public ObjectNode(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean addChild(String property, Node value) {
        children.put(property, value);
        return true;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void write(Writer writer) {
        writer.write(this);
    }

    @Override
    public String toString() {
        return "GeneratorObjectNode{" +
                "children=" + children +
                '}';
    }
}
