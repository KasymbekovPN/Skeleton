package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SkeletonCollector implements Collector {

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollector.class);

    private final CollectorStructure collectorStructure;

    private Node root;
    private Node target;

    public SkeletonCollector(CollectorStructure collectorStructure, boolean defaultProtocolData) {
        this.collectorStructure = collectorStructure;
        clear();
    }

    @Override
    public void clear() {
        target = root = new ObjectNode(null);
    }

    @Override
    public void reset() {
        while (target.getParent() != null){
            target = target.getParent();
        }
    }

    @Override
    public void beginObject(String property) {
        target.addChild(property, new ObjectNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginObject() {
        target.addChild(new ObjectNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void addProperty(String property, String value) {
        target.addChild(property, new StringNode(target, value));
    }

    @Override
    public void addProperty(String property, Number value) {
        target.addChild(property, new NumberNode(target, value));
    }

    @Override
    public void addProperty(String property, Boolean value) {
        target.addChild(property, new BooleanNode(target, value));
    }

    @Override
    public void addProperty(String property, Character value) {
        target.addChild(property, new CharacterNode(target, value));
    }

    @Override
    public void beginArray(String property) {
        target.addChild(property, new ArrayNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginArray() {
        target.addChild(new ArrayNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void addProperty(String value) {
        target.addChild(new StringNode(target,value));
    }

    @Override
    public void addProperty(Number value) {
        target.addChild(new NumberNode(target, value));
    }

    @Override
    public void addProperty(Boolean value) {
        target.addChild(new BooleanNode(target, value));
    }

    @Override
    public void addProperty(Character value) {
        target.addChild(new CharacterNode(target, value));
    }

    @Override
    public void end() {
        if (null != target.getParent()){
            target = target.getParent();
        }
    }

    @Override
    public void setTarget(List<String> path) {
        reset();
        setEachTarget(new ArrayList<>(path));
    }

    @Override
    public CollectorStructure getCollectorStructure() {
        return collectorStructure;
    }

    @Override
    public Node getNode() {
        return root;
    }

    @Override
    public Node detachNode() {
        Node node = this.root;
        clear();
        return node;
    }

    @Override
    public Node attachNode(Node node) {
        Node oldRoot = root;
        root = node;
        return oldRoot;
    }

    private void setEachTarget(List<String> path){
        String pathItem = path.remove(0);
        beginObject(pathItem);
        if (path.size() > 0){
            setEachTarget(path);
        }
    }

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
