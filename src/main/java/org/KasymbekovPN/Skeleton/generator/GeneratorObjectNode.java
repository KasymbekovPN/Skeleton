package org.KasymbekovPN.Skeleton.generator;

import java.util.HashMap;
import java.util.Map;

public class GeneratorObjectNode implements GeneratorNode {

    private GeneratorNode parent;
    private Map<String, GeneratorNode> children = new HashMap<>();

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
    public String toString() {
        return "GeneratorObjectNode{" +
                "children=" + children +
                '}';
    }
}
