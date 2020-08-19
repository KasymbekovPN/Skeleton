package org.KasymbekovPN.Skeleton.lib.node;

import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;

import java.util.Optional;

/**
 * Implementations of Node must implement constructor:
 *  1. ImplementationName(Node parent)
 */
public interface Node {
    void apply(Task<Node> task);
    Node getParent();
    Node deepCopy(Node parent);
    EntityItem getEI();
    boolean is(EntityItem ei);
    default Optional<Node> addChild(String property, Node value) {
        return Optional.empty();
    };
    default Optional<Node> addChild(Node value){
        return Optional.empty();
    };
    default boolean containsKey(String key) {return false;};
    default Optional<Node> getChild(CollectorPath collectorPath) {return Optional.empty();}
    default Optional<Node> get(String property, EntityItem ei) {return Optional.empty();}
}
