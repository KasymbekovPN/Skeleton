package org.KasymbekovPN.Skeleton.lib.format.writing.formatter;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

import java.util.List;

public interface WritingFormatter {
    StringDecoder getBeginBorder();
    StringDecoder getEndBorder();
    StringDecoder getValue(Node node);
    StringDecoder getPropertyName(String propertyName);
    List<StringDecoder> getDelimiters(int size);
}
