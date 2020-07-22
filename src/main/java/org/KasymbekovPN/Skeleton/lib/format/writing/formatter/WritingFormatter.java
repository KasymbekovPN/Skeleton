package org.KasymbekovPN.Skeleton.lib.format.writing.formatter;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

//< rename
public interface WritingFormatter {
    StringDecoder getBeginBorder();
    StringDecoder getEndBorder();
    StringDecoder getValue(Node node);
    StringDecoder getPropertyName(String propertyName);
}
