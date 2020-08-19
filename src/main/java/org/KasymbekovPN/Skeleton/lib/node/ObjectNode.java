package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ObjectNode implements Node {

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
    public void apply(Task<Node> task) {
        task.handle(this);
    }

    @Override
    public boolean containsKey(String key) {
        return children.containsKey(key);
    }

    @Override
    public Optional<Node> get(String property, EntityItem ei) {
        if (children.containsKey(property)){
            Node node = children.get(property);
            if (node.getEI().equals(ei)){
                return Optional.of(node);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean is(EntityItem ei) {
        return ei().equals(ei);
    }

    @Override
    public Optional<Node> getChild(CollectorPath collectorPath) {

        Node bufferNode = this;
        Iterator<Pair<String, EntityItem>> iterator = collectorPath.iterator();
        while (iterator.hasNext()){
            Pair<String, EntityItem> next = iterator.next();
            String propertyName = next.getLeft();
            EntityItem propertyType = next.getRight();

            Optional<Node> mayBeInnerNode = bufferNode.get(propertyName, propertyType);
            if (mayBeInnerNode.isPresent()){
                bufferNode = mayBeInnerNode.get();
            } else {
                bufferNode = null;
                break;
            }
        }

        return bufferNode != null
                ? Optional.of(bufferNode)
                : Optional.empty();
    }

    @Override
    public String toString() {
        return "ObjectNode{ " + children + " }";
    }
}
