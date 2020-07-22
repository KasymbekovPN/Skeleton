package org.KasymbekovPN.Skeleton.lib.format.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

//< rename
public interface WritingFormatterHandler {
    StringDecoder getBeginBorder();
    StringDecoder getEndBorder();
    StringDecoder getValue(Node node);
    StringDecoder getPropertyName(String propertyName);
}
