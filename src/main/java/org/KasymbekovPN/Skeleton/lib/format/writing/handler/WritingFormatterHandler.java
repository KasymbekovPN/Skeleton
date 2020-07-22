package org.KasymbekovPN.Skeleton.lib.format.writing.formatter;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

public interface WritingFormatter {
    StringDecoder getDecoder();
    void reset();
    void addBeginBorder(Node node);
    void addEndBorder(Node node);
    void addValue(Node node);
    void addPropertyName(Node node, String propertyName);
}
