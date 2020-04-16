package org.KasymbekovPN.Skeleton.generator.node;

import org.KasymbekovPN.Skeleton.generator.writer.Writer;

import java.util.Optional;

public interface Node {
    Node getParent();
    default Optional<Node> addChild(String property, Node value) {
        return Optional.empty();
    };
    default Optional<Node> addChild(Node value){
        return Optional.empty();
    };
    default void write(Writer writer){
    }
}
