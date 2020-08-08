package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.HashSet;
import java.util.Set;

public class JsonBooleanNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Set<Character> VALUE_END_TRIGGERS = new HashSet<>(){{
        add(',');
        add('}');
        add(']');
    }};

    public JsonBooleanNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                              NodeDeserializerHandler parentHandler,
                                              Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {
        String rawValue = getRawValue();
        BooleanNode booleanNode = new BooleanNode(parentNode, Boolean.valueOf(rawValue));
        parentHandler.setChildNode(booleanNode);
        return parentHandler;
    }

    private String getRawValue(){
        StringBuilder rawValue = new StringBuilder();
        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();

            if (VALUE_END_TRIGGERS.contains(next)){
                dataWrapper.decIterator();
                break;
            }

            rawValue.append(next);
        }
        return rawValue.toString();
    }
}

