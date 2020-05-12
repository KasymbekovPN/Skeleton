package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.protocol.SkeletonProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkeletonCollector implements Collector {

    private static final Logger log = LoggerFactory.getLogger(SkeletonCollector.class);

    private final CollectorStructure collectorStructure;

    private Node root;
    private Node target;

    public SkeletonCollector(CollectorStructure collectorStructure) {
        this.collectorStructure = collectorStructure;
        clear();
    }

    @Override
    public void clear() {
        target = root = new SkeletonObjectNode(null);
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
        target.addChild(property, new SkeletonObjectNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginObject() {
        target.addChild(new SkeletonObjectNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void addProperty(String property, String value) {
        target.addChild(property, new SkeletonStringNodeSkeleton(target, value));
    }

    @Override
    public void addProperty(String property, Number value) {
        target.addChild(property, new SkeletonNumberNodeSkeleton(target, value));
    }

    @Override
    public void addProperty(String property, Boolean value) {
        target.addChild(property, new SkeletonBooleanNodeSkeleton(target, value));
    }

    @Override
    public void addProperty(String property, Character value) {
        target.addChild(property, new SkeletonCharacterNodeSkeleton(target, value));
    }

    @Override
    public void beginArray(String property) {
        target.addChild(property, new SkeletonArrayNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginArray() {
        target.addChild(new SkeletonArrayNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void addProperty(String value) {
        target.addChild(new SkeletonStringNodeSkeleton(target,value));
    }

    @Override
    public void addProperty(Number value) {
        target.addChild(new SkeletonNumberNodeSkeleton(target, value));
    }

    @Override
    public void addProperty(Boolean value) {
        target.addChild(new SkeletonBooleanNodeSkeleton(target, value));
    }

    @Override
    public void addProperty(Character value) {
        target.addChild(new SkeletonCharacterNodeSkeleton(target, value));
    }

    @Override
    public void end() {
        if (null != target.getParent()){
            target = target.getParent();
        }
    }

    @Override
    public void apply(CollectorProcess collectorProcess) {
        root.apply(collectorProcess);
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
                    if (((SkeletonObjectNode) bufferNode).containsKey(s)) {
                        bufferNode = ((SkeletonObjectNode) bufferNode).getChildren().get(s);
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
        setTarget(collectorStructure.getPath(CollectorStructureEI.protocolEI()));
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
