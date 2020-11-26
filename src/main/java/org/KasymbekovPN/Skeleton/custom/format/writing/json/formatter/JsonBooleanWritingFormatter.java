package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

import java.util.ArrayList;
import java.util.List;

public class JsonBooleanWritingFormatter implements WritingFormatter {

    private final Offset offset;

    public JsonBooleanWritingFormatter(Offset offset) {
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
                node.is(BooleanNode.ei())
                    ? String.valueOf(((BooleanNode)node).getValue())
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
