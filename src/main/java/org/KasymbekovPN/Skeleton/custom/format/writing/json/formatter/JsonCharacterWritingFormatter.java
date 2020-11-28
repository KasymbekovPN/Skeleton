package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.node.CharacterNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;

public class JsonCharacterWritingFormatter extends JsonBaseWritingFormatter {

    private static final String VALUE_BORDER = "'";

    public JsonCharacterWritingFormatter(Offset offset) {
        super(offset);
    }

    @Override
    public StringDecoder getValue(Node node) {
        return new StringStringDecoder(
                node.is(CharacterNode.ei())
                    ? VALUE_BORDER + String.valueOf(((CharacterNode)node).getValue()) + VALUE_BORDER
                        : ""
        );
    }
}
