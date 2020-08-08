package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.Optional;

public class JsonObjectNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Character NAME_BEGIN_TRIGGER = '"';
    private static final Character NAME_END_TRIGGER = '"';
    private static final Character VALUE_TYPE_DEF_TRIGGER = ':';
    private static final Character THIS_END_TRIGGER = '}';

    private StringBuilder property = new StringBuilder();

    public JsonObjectNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                             NodeDeserializerHandler parentHandler,
                                             Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
        node = new ObjectNode(parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {

        State state = State.INIT;

        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();

            switch (state){
                case INIT:
                    if (next.equals(NAME_BEGIN_TRIGGER)){
                        property.setLength(0);
                        state = State.NAME_DEFINITION;
                    } else if (next.equals(THIS_END_TRIGGER)){
                        parentHandler.setChildNode(node);
                        return parentHandler;
                    }
                    break;
                case NAME_DEFINITION:
                    if (next.equals(NAME_END_TRIGGER)){
                        state = State.WAIT_SEPARATOR;
                    } else {
                        property.append(next);
                    }
                    break;
                case WAIT_SEPARATOR:
                    if (next.equals(VALUE_TYPE_DEF_TRIGGER)){
                        state = State.VALUE_TYPE_DEFINITION;
                    }
                    break;
                case VALUE_TYPE_DEFINITION:
                    Optional<NodeDeserializerHandler> mayBeHandler = createChildHandler(next, node);
                    if (mayBeHandler.isPresent()){
                        return mayBeHandler.get();
                    }
                    break;
            }
        }

        return parentHandler;
    }

    @Override
    public void setChildNode(Node node) {
        this.node.addChild(property.toString(), node);
    }

    private enum State {
        INIT,
        NAME_DEFINITION,
        WAIT_SEPARATOR,
        VALUE_TYPE_DEFINITION
    }
}
