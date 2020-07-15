package org.KasymbekovPN.Skeleton.lib.format.writing.formatter;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

public interface WritingFormatter {
    StringDecoder getDecoder();
    void reset();
    void addBeginBorder(Node node);
    void addEndBorder(Node node);
    void addValue(Node node);

    //<
    WritingFormatter addHandler(EntityItem handlerId, WritingFormatterHandler handler);
    //<
//    void handle(Node node);
}
