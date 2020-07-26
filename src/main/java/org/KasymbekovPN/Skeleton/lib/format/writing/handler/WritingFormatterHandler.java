package org.KasymbekovPN.Skeleton.lib.format.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

import java.util.Iterator;
import java.util.List;

public interface WritingFormatterHandler {
    StringDecoder getDecoder();
    void reset();
    List<String> getDelimiters(int size, Node node);
    void addDelimiter(Iterator<String> delimiterIterator);
    void addBeginBorder(Node node);
    void addEndBorder(Node node);
    void addValue(Node node);
    void addPropertyName(Node node, String propertyName);
}
