package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.HashSet;
import java.util.Set;

public class JsonNumberNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Set<Character> NUMBER_END_TRIGGERS = new HashSet<>(){{
        add(',');
        add(']');
        add('}');
    }};

    public JsonNumberNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                             NodeDeserializerHandler parentHandler,
                                             Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {
        Number value = getValue();
        NumberNode numberNode = new NumberNode(parentNode, value);
        parentHandler.setChildNode(numberNode);
        return parentHandler;
    }

    private Number getValue(){
        StringBuilder rawValue = new StringBuilder();
        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();
            if (NUMBER_END_TRIGGERS.contains(next)){
                dataWrapper.decIterator();
                break;
            } else {
                rawValue.append(next);
            }
        }

        return Double.valueOf(rawValue.toString());
    }
}