package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorHandingProcess;

import java.util.List;
import java.util.Optional;

public interface Node {
    void apply(CollectorHandingProcess collectorHandingProcess);
    Node getParent();
    default Optional<Node> addChild(String property, Node value) {
        return Optional.empty();
    };
    default Optional<Node> addChild(Node value){
        return Optional.empty();
    };
    default boolean containsKey(String key) {return false;};
    default boolean isArray() {return false;}
    default boolean isBoolean() {return false;}
    default boolean isCharacter() {return false;}
    default boolean isNumber() {return false;}
    default boolean isObject() {return false;}
    default boolean isPrimitive() {return false;}
    default boolean isString() {return false;}
    default Optional<Node> getChild(List<String> path, Class<? extends  Node> clazz) {return Optional.empty();}
}
