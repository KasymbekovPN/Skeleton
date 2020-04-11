package org.KasymbekovPN.Skeleton.generator;

import java.util.HashMap;
import java.util.Map;

public class GeneratorObjectNode implements GeneratorNode {

    private GeneratorNode parent;
    private Map<String, GeneratorNode> children = new HashMap<>();

    public Map<String, GeneratorNode> getChildren() {
        return children;
    }

    public GeneratorObjectNode(GeneratorNode parent) {
        this.parent = parent;
    }

    @Override
    public boolean addChild(String property, GeneratorNode value) {
        children.put(property, value);
        return true;
    }

    @Override
    public GeneratorNode getParent() {
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
