package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;

public class JsonNumberWritingFormatter extends JsonBaseWritingFormatter {

    public JsonNumberWritingFormatter(Offset offset) {
        super(offset);
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder(
                node.is(NumberNode.ei())
                    ? String.valueOf(((NumberNode)node).getValue())
                        : ""
        );
    }
}
