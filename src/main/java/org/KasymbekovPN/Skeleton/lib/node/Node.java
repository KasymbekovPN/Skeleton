package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

import java.util.List;
import java.util.Optional;

/**
 * Implementations of Node must implement constructor:
 *  1. ImplementationName(Node parent)
 */
public interface Node {
    void apply(CollectorProcess collectorProcess);
    //<
    void apply(Task<Node> task);
    //<
    Node getParent();
    Node deepCopy(Node parent);
    EntityItem getEI();
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
    default Optional<Node> get(String property, Class<? extends Node> clazz) {return Optional.empty();}
    default void deepSet(Node node){}
}
