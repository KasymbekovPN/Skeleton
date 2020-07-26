package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

import java.util.ArrayList;
import java.util.List;

public class JsonStringWritingFormatter implements WritingFormatter {

    private static final String VALUE_BORDER = "\"";

    private final Offset offset;

    public JsonStringWritingFormatter(Offset offset) {
        this.offset = offset;
    }

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
                node.isString()
                    ? VALUE_BORDER + ((StringNode)node).getValue() + VALUE_BORDER
                        : ""
        );
    }

    @Override
    public StringDecoder getPropertyName(String propertyName) {
        return new StringStringDecoder();
    }

    @Override
    public List<StringDecoder> getDelimiters(int size) {
        return new ArrayList<>();
    }
}
