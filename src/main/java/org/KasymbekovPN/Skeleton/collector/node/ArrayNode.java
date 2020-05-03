package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayNode implements Node {

    private Node parent;
    private List<Node> children = new ArrayList<>();

    public List<Node> getChildren() {
        return children;
    }

    public ArrayNode(Node parent) {
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

//    @Override
//    public void write(Writer writer) {
//        writer.write(this);
//    }
    //<
    @Override
    public void doIt(CollectorHandingProcess collectorHandingProcess) {
        collectorHandingProcess.doIt(this);
    }

    @Override
    public String toString() {
        return "GeneratorArrayNode{" +
                "children=" + children +
                '}';
    }
}
