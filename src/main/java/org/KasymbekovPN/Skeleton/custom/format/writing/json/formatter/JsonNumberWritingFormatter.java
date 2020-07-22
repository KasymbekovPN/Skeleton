package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

public class JsonNumberWritingFormatter implements WritingFormatter {

    @Override
    public StringDecoder getBeginBorder() {
        return new StringStringDecoder();
    }

    @Override
    public StringDecoder getEndBorder() {
        return new StringStringDecoder();
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder(
                node.isNumber()
                    ? String.valueOf(((NumberNode)node).getValue())
                        : ""
        );
    }

    @Override
    public StringDecoder getPropertyName(String propertyName) {
        return new StringStringDecoder();
    }
}
