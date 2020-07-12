package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.CharacterNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;

public class JsonCharacterNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Character CHARACTER_BEGIN_TRIGGER = '\'';
    private static final Character CHARACTER_END_TRIGGER = '\'';

    public JsonCharacterNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                                NodeDeserializerHandler parentHandler,
                                                Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {
        Character value = getValue();
        if (value != null){
            CharacterNode characterNode = new CharacterNode(parentNode, value);
            parentHandler.setChildNode(characterNode);
        }

        return parentHandler;
    }

    private Character getValue(){
        Character ch = null;
        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();
            if (next.equals(CHARACTER_BEGIN_TRIGGER)){
                if (dataWrapper.hasNext()){
                    ch = dataWrapper.next();
                    break;
                }
            }
        }

        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();
            if (next.equals(CHARACTER_END_TRIGGER)){
                break;
            }
        }

        return ch;
    }
}
