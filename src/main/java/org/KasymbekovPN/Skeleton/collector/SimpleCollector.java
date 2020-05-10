package org.KasymbekovPN.Skeleton.collector;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;
import org.KasymbekovPN.Skeleton.collector.node.*;
import org.KasymbekovPN.Skeleton.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.format.collector.CollectorStructureItem;
import org.KasymbekovPN.Skeleton.protocol.SkeletonProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//< SKEL-31 --- may be replace simple with skeleton
public class SimpleCollector implements Collector {

    private static final Logger log = LoggerFactory.getLogger(SimpleCollector.class);

    private final CollectorStructure collectorStructure;

    private Node root;
    private Node target;

    public SimpleCollector(CollectorStructure collectorStructure) {
        this.collectorStructure = collectorStructure;
        clear();
    }

    @Override
    public void clear() {
        target = root = new ObjectNode(null);
        addProtocolObject();
    }

    @Override
    public void reset() {
        while (target.getParent() != null){
            target = target.getParent();
            end();
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
    public void apply(CollectorHandingProcess collectorHandingProcess) {
        root.apply(collectorHandingProcess);
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
    public Optional<Node> getNodeByPath(Node node, List<String> path, Class<? extends Node> clazz) {

        Node bufferNode = null;
        if (node.isObject()){
            bufferNode = node;
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
        }

        return bufferNode != null && bufferNode.getClass().equals(clazz)
                ? Optional.of(bufferNode)
                : Optional.empty();
    }

    private void setEachTarget(List<String> path){
        String pathItem = path.remove(0);
        beginObject(pathItem);
        if (path.size() > 0){
            setEachTarget(path);
        }
    }

    private void addProtocolObject(){
        setTarget(collectorStructure.getPath(CollectorStructureItem.PROTOCOL));
        addProperty("version", SkeletonProtocol.getInstance().getVersion());
        reset();
    }

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
