package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

public class JsonArrayWritingFormatter implements WritingFormatter {

    private static final String BEGIN_BORDER = "[";
    private static final String END_BORDER = "]";

    @Override
    public StringDecoder getBeginBorder() {
        return new StringStringDecoder(BEGIN_BORDER);
    }

    @Override
    public StringDecoder getEndBorder() {
        return new StringStringDecoder(END_BORDER);
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder();
    }

    @Override
    public StringDecoder getPropertyName(String propertyName) {
        return new StringStringDecoder();
    }
}
