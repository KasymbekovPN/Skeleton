package org.KasymbekovPN.Skeleton.generator.formatter;

import org.KasymbekovPN.Skeleton.generator.node.*;

import java.util.*;

public class JsonFormatter implements Formatter {

    private static final String DEFAULT_BOR$DER = "";

    private Map<Class<? extends Node>, String> beginBorder = new HashMap<>(){{
        put(ObjectNode.class, "{");
        put(ArrayNode.class, "[");
        put(StringNode.class, "\"");
        put(CharacterNode.class, "'");
    }};

    private Map<Class<? extends Node>, String> endBorder = new HashMap<>(){{
        put(ObjectNode.class, "}");
        put(ArrayNode.class, "]");
        put(StringNode.class, "\"");
        put(CharacterNode.class, "'");
    }};

    private String nameBorder = "\"";
    private String nameValueSeparator = ":";
    private String firstDelimiter = "";
    private String delimiter = ",";

    @Override
    public String getBeginBorder(Class<? extends Node> clazz) {
        return beginBorder.getOrDefault(clazz, DEFAULT_BOR$DER);
    }

    @Override
    public String getEndBorder(Class<? extends Node> clazz) {
        return endBorder.getOrDefault(clazz, DEFAULT_BOR$DER);
    }

    @Override
    public List<String> getDelimiters(Class<? extends Node> clazz, int size) {
        ArrayList<String> delimiters = new ArrayList<>(){{add(firstDelimiter);}};
        for (int i = 1; i < size; i++) {
            delimiters.add(delimiter);
        }
        return delimiters;
    }

    @Override
    public String getNameBorder() {
        return nameBorder;
    }

    @Override
    public String getNameValueSeparator() {
        return nameValueSeparator;
    }
}
