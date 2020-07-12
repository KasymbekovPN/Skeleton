package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class JsonBaseNodeDeserializerHandler implements NodeDeserializerHandler {

    private static final Character ARRAY_TRIGGER = '[';
    private static final Set<Character> BOOLEAN_TRIGGERS = new HashSet<>(){{
        add('T');
        add('F');
        add('t');
        add('f');
    }};
    private static final Character CHARACTER_TRIGGER = '\'';
    private static final Character OBJECT_TRIGGER = '{';
    private static final Character STRING_TRIGGER = '"';

    protected final NodeSerializedDataWrapper dataWrapper;
    protected final NodeDeserializerHandler parentHandler;
    protected final Node parentNode;

    protected Node node;

    public JsonBaseNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                           NodeDeserializerHandler parentHandler,
                                           Node parentNode) {
        this.dataWrapper = dataWrapper;
        this.parentHandler = parentHandler;
        this.parentNode = parentNode;
    }

    protected Optional<NodeDeserializerHandler> createChildHandler(Character next, Node parentNode){

        Optional<NodeDeserializerHandler> result = Optional.empty();

        if (next.equals(ARRAY_TRIGGER)){
            dataWrapper.decIterator();
            result = Optional.of(new JsonArrayNodeDeserializerHandler(dataWrapper, this, parentNode));
        } else if (BOOLEAN_TRIGGERS.contains(next)){
            dataWrapper.decIterator();
            result = Optional.of(new JsonBooleanNodeDeserializerHandler(dataWrapper, this, parentNode));
        } else if (next.equals(CHARACTER_TRIGGER)){
            dataWrapper.decIterator();
            result = Optional.of(new JsonCharacterNodeDeserializerHandler(dataWrapper, this, parentNode));
        } else if (Character.isDigit(next)){
            dataWrapper.decIterator();
            result = Optional.of(new JsonNumberNodeDeserializerHandler(dataWrapper, this, parentNode));
        } else if (next.equals(OBJECT_TRIGGER)){
            dataWrapper.decIterator();
            result = Optional.of(new JsonObjectNodeDeserializerHandler(dataWrapper, this, parentNode));
        } else if (next.equals(STRING_TRIGGER)){
            result = Optional.of(new JsonStringNodeDeserializerHandler(dataWrapper, this, parentNode));
        }

        return result;
    }
}
