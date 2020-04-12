package org.KasymbekovPN.Skeleton.generator.node;

import org.KasymbekovPN.Skeleton.generator.writer.Writer;

public interface Node {
    Node getParent();
    default boolean addChild(String property, Node value) {
        return false;
    };
    default boolean addChild(Node value){
        return false;
    };
    default void write(Writer writer){
    }
}
