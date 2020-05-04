package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;

import java.util.Optional;

public interface Node {
    void doIt(CollectorHandingProcess collectorHandingProcess);
    Node getParent();
    default Optional<Node> addChild(String property, Node value) {
        return Optional.empty();
    };
    default Optional<Node> addChild(Node value){
        return Optional.empty();
    };
    default boolean containsKey(String key) {return false;};
}
