package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;

public class JsonStringWritingFormatter extends JsonBaseWritingFormatter {

    private static final String VALUE_BORDER = "\"";

    public JsonStringWritingFormatter(Offset offset) {
        super(offset);
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder(
                node.is(StringNode.ei())
                    ? VALUE_BORDER + ((StringNode)node).getValue() + VALUE_BORDER
                        : ""
        );
    }
}
