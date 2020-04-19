package org.KasymbekovPN.Skeleton.generator.node;

import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ObjectNode implements Node {

    private static final Logger log = LoggerFactory.getLogger(ObjectNode.class);

    private Node parent;
    private Map<String, Node> children = new HashMap<>();

    public Map<String, Node> getChildren() {
        return children;
    }

    public ObjectNode(Node parent) {
        this.parent = parent;
    }

    @Override
    public Optional<Node> addChild(String property, Node value) {
        if (children.containsKey(property)){
            return Optional.of(children.get(property));
        }

        children.put(property, value);
        return Optional.of(value);
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
                "}";
    }
}