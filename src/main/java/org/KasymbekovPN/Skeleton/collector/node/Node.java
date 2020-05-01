package org.KasymbekovPN.Skeleton.collector.node;

import org.KasymbekovPN.Skeleton.collector.writer.Writer;

import java.util.Optional;

public interface Node {
    Node getParent();
    default Optional<Node> addChild(String property, Node value) {
        return Optional.empty();
    };
    default Optional<Node> addChild(Node value){
        return Optional.empty();
    };
    default void write(Writer writer){}
    default boolean containsKey(String key) {return false;};
}
