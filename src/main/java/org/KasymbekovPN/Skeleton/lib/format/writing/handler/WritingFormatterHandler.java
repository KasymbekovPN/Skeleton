package org.KasymbekovPN.Skeleton.lib.format.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

public interface WritingFormatterHandler {
    default void addBeginBorder(){}
    default void addEndBorder(){}
    default void addValue(Node node){}
    default void addPropertyName(String propertyName){}
}
