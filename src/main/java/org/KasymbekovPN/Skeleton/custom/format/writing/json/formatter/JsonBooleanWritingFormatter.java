package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;

public class JsonBooleanWritingFormatter extends JsonBaseWritingFormatter {

    public JsonBooleanWritingFormatter(Offset offset) {
        super(offset);
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder(
                node.is(BooleanNode.ei())
                    ? String.valueOf(((BooleanNode)node).getValue())
                        : ""
        );
    }
}
