package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;

public class JsonStringNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Character SHIELD = '\\';
    private static final Character STRING_END_TRIGGER = '"';

    public JsonStringNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                             NodeDeserializerHandler parentHandler,
                                             Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {
        String value = getValue();
        StringNode stringNode = new StringNode(parentNode, value);
        parentHandler.setChildNode(stringNode);
        return parentHandler;
    }

    private String getValue(){
        boolean shielded = false;
        StringBuilder rawValue = new StringBuilder();
        while(dataWrapper.hasNext()){
            Character next = dataWrapper.next();

            if (shielded){
                shielded = false;
                rawValue.append(next);
            } else {
                if (next.equals(STRING_END_TRIGGER)){
                    break;
                } else {
                    if (next.equals(SHIELD)){
                        shielded = true;
                    }
                    rawValue.append(next);
                }
            }
        }

        return rawValue.toString();
    }
}