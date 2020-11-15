package org.KasymbekovPN.Skeleton.custom.converter;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeModeOld;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.Set;

public class Str2NodeConverter implements Converter<Node, Triple<Node, String, Des2NodeModeOld>> {

    private static final Set<String> ALLOWED_BOOLEAN_VALUE = new HashSet<>(){{
        add("true");
        add("false");
    }};
    private static final char CHAR_BORDER = '\'';
    private static final char STRING_BORDER = '"';
    private static final char NUMBER_SEPARATOR = '.';

    @Override
    public Node convert(Triple<Node, String, Des2NodeModeOld> value) {

        Node parent = value.getLeft();
        String raw = value.getMiddle();
        Des2NodeModeOld mode = value.getRight();

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
        int length = raw.length();
        boolean hasInvalidCharacter = false;
        int separatorCounter = 0;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(raw.charAt(i))){
                hasInvalidCharacter = true;
            }
            if (raw.charAt(i) == NUMBER_SEPARATOR){
                separatorCounter++;
            }
        }

        String status = "";
        if (hasInvalidCharacter){
            status += "has invalid character; ";
        }
        if (separatorCounter > 1){
            status += "has more than one separator";
        }
        if (status.isEmpty()){
            return new NumberNode(parent, Double.valueOf(raw));
        }

        return new InvalidNode(parent, status, raw);
    }

    private Node handleString(String raw, Node parent){
        int length = raw.length();
        if (raw.charAt(0) == STRING_BORDER && raw.charAt(length - 1) == STRING_BORDER){
            return new StringNode(parent, raw.substring(1, length - 1));
        }

        return new InvalidNode(parent, "Value isn't string", raw);
    }

    private Node handleCharacter(String raw, Node parent){
        if (raw.length() == 3 && raw.charAt(0) == CHAR_BORDER && raw.charAt(2) == CHAR_BORDER){
            return new CharacterNode(parent, raw.charAt(1));
        }

        return new InvalidNode(parent, "Value isn't char", raw);
    }

    private Node handleInvalid(Node parent, Des2NodeModeOld mode){
        return new InvalidNode(parent, "Wrong mode", mode.toString());
    }
}
