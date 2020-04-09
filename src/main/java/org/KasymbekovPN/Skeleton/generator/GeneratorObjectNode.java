package org.KasymbekovPN.Skeleton.generator;

import java.util.HashMap;
import java.util.Map;

public class GeneratorObjectNode implements GeneratorNode {

    private GeneratorNode parent;
    private Map<String, GeneratorNode> children = new HashMap<>();

    public GeneratorObjectNode(GeneratorNode parent) {
        this.parent = parent;
    }

    public void addChild(String property, GeneratorNode generatorNode){
        children.put(property, generatorNode);
    }

    @Override
    public GeneratorNode getParent() {
        return parent;
    }
}
