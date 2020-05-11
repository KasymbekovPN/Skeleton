package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkeletonArrayNode implements Node {

    private Node parent;
    private List<Node> children = new ArrayList<>();

    public List<Node> getChildren() {
        return children;
    }

    public SkeletonArrayNode(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Optional<Node> addChild(Node value) {
        children.add(value);
        return Optional.of(value);
    }

    @Override
    public void apply(CollectorProcess collectorProcess) {
        collectorProcess.handle(this);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public String toString() {
        return "GeneratorArrayNode{" +
                "children=" + children +
                '}';
    }
}
