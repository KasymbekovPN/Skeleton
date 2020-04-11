package org.KasymbekovPN.Skeleton.generator;

import java.util.ArrayList;
import java.util.List;

public class GeneratorArrayNode implements GeneratorNode {

    private GeneratorNode parent;
    private List<GeneratorNode> children = new ArrayList<>();

    public GeneratorArrayNode(GeneratorNode parent) {
        this.parent = parent;
    }

    @Override
    public GeneratorNode getParent() {
        return parent;
    }

    @Override
    public boolean addChild(GeneratorNode value) {
        return children.add(value);
    }

    @Override
    public void write(Writer writer) {
        writer.write(this);
    }

    @Override
    public String toString() {
        return "GeneratorArrayNode{" +
                "children=" + children +
                '}';
    }
}
