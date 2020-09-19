package org.KasymbekovPN.Skeleton.custom.converter;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.InvalidNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.Set;

public class Str2NodeConverter implements Converter<Node, Triple<Node, String, Des2NodeMode>> {

    private static final Set<String> ALLOWED_BOOLEAN_VALUE = new HashSet<>(){{
        add("true");
        add("false");
    }};

    @Override
    public Node convert(Triple<Node, String, Des2NodeMode> value) {

        Node parent = value.getLeft();
        String raw = value.getMiddle();
        Des2NodeMode mode = value.getRight();

        switch (mode){
            case BOOLEAN:
                return handleBoolean(raw, parent);
            case CHARACTER:
                return handleCharacter(raw, parent);
            case NUMBER:
                return handleNumber(raw, parent);
            case STRING:
                return handleString(raw, parent);
            default:
                return handleInvalid(parent, mode);
        }
    }

    private Node handleBoolean(String raw, Node parent){
        String line = raw.trim();
        line = line.toLowerCase();

        if (ALLOWED_BOOLEAN_VALUE.contains(line)){
            return new BooleanNode(parent, Boolean.valueOf(line));
        }

        return new InvalidNode(parent, "Value isn't boolean", raw);
    }

    private Node handleNumber(String raw, Node parent){
        return null;
    }

    private Node handleString(String raw, Node parent){
        return null;
    }

    private Node handleCharacter(String raw, Node parent){
        return null;
    }

    private Node handleInvalid(Node parent, Des2NodeMode mode){
        return new InvalidNode(parent, "Wroong mode", mode.toString());
    }
}
