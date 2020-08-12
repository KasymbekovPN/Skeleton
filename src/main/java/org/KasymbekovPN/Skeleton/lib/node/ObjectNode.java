package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
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

    public static EntityItem ei(){
        return NodeEI.objectEI();
    }

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
    public Node deepCopy(Node parent) {
        ObjectNode objectNode = new ObjectNode(parent);
        for (Map.Entry<String, Node> entry : children.entrySet()) {
            objectNode.children.put(entry.getKey(), entry.getValue().deepCopy(objectNode));
        }

        return objectNode;
    }

    @Override
    public EntityItem getEI() {
        return ei();
    }

    @Override
    public void apply(CollectorProcess collectorProcess) {
        collectorProcess.handle(this);
    }

    @Override
    public void apply(Task<Node> task) {
        task.handle(this);
    }

    @Override
    public boolean containsKey(String key) {
        return children.containsKey(key);
    }

    @Override
    public Optional<Node> get(String property, Class<? extends Node> clazz) {
        if (children.containsKey(property)){
            Node node = children.get(property);
            if (node.getClass().equals(clazz)){
                return Optional.of(node);
            }
        }

        return Optional.empty();
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
    public void deepSet(Node node) {
        if (node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            parent = objectNode.getParent();
            children = objectNode.getChildren();
        }
    }

    @Override
    public String toString() {
        return "GeneratorObjectNode{" +
                "children=" + children +
                "}";
    }
}
