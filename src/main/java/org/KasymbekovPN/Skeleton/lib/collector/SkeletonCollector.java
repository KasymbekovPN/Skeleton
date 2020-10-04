package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.lib.node.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class SkeletonCollector implements Collector {

    private Node root;
    private Node target;

    public SkeletonCollector() {
        clear();
    }

    @Override
    public void clear() {
        target = root = new ObjectNode(null);
    }

    @Override
    public void reset() {
        target = root;
    }

    @Override
    public Node beginObject(String property) {
        target.addChild(property, new ObjectNode(target)).ifPresent(value -> target = value);
        return target;
    }

    @Override
    public Node beginObject() {
        target.addChild(new ObjectNode(target)).ifPresent(value -> target = value);
        return target;
    }

    @Override
    public Node addProperty(String property, String value) {
        target.addChild(property, new StringNode(target, value));
        return target;
    }

    @Override
    public Node addProperty(String property, Number value) {
        target.addChild(property, new NumberNode(target, value));
        return target;
    }

    @Override
    public Node addProperty(String property, Boolean value) {
        target.addChild(property, new BooleanNode(target, value));
        return target;
    }

    @Override
    public Node addProperty(String property, Character value) {
        target.addChild(property, new CharacterNode(target, value));
        return target;
    }

    @Override
    public Node beginArray(String property) {
        target.addChild(property, new ArrayNode(target)).ifPresent(value -> target = value);
        return target;
    }

    @Override
    public Node beginArray() {
        target.addChild(new ArrayNode(target)).ifPresent(value -> target = value);
        return target;
    }

    @Override
    public Node addProperty(String value) {
        target.addChild(new StringNode(target,value));
        return target;
    }

    @Override
    public Node addProperty(Number value) {
        target.addChild(new NumberNode(target, value));
        return target;
    }

    @Override
    public Node addProperty(Boolean value) {
        target.addChild(new BooleanNode(target, value));
        return target;
    }

    @Override
    public Node addProperty(Character value) {
        target.addChild(new CharacterNode(target, value));
        return target;
    }

    @Override
    public Node end() {
        if (null != target.getParent()){
            target = target.getParent();
        }
        return target;
    }

    @Override
    public Node setTarget(List<String> path) {
        reset();
        setEachTarget(new ArrayList<>(path));

        return target;
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
    public Pair<Node, Node> attach(Node root, Node target) {
        Node oldRoot = this.root;
        Node oldTarget = this.target;
        this.root = root;
        this.target = target;
        return new MutablePair<>(oldRoot, oldTarget);
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
        return "SkeletonCollector{ " + root + " }";
    }
}
