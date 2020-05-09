package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
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
    public void apply(CollectorHandingProcess collectorHandingProcess) {
        collectorHandingProcess.handle(this);
    }

    @Override
    public boolean containsKey(String key) {
        return children.containsKey(key);
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public Optional<Node> getChild(List<String> path, Class<? extends Node> clazz) {
        Node bufferNode = this;
        for (String s : path) {
            if (bufferNode.isObject()) {
                if (((ObjectNode) bufferNode).containsKey(s)) {
                    bufferNode = ((ObjectNode) bufferNode).getChildren().get(s);
                } else {
                    bufferNode = null;
                    break;
                }
            } else {
                bufferNode = null;
                break;
            }
        }

        return bufferNode != null && bufferNode.getClass().equals(clazz)
                ? Optional.of(bufferNode)
                : Optional.empty();
    }

    @Override
    public String toString() {
        return "GeneratorObjectNode{" +
                "children=" + children +
                "}";
    }
}
