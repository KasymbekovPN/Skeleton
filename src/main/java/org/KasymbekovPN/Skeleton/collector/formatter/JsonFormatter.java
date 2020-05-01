package org.KasymbekovPN.Skeleton.collector.formatter;

import org.KasymbekovPN.Skeleton.collector.node.*;

import java.util.*;

public class JsonFormatter implements Formatter {

    private static final String OFFSET_MARKER = "%offset%";
    private static final String DEFAULT_BORDER = "";
    private static final String DEFAULT_DELIMITER = "";

    private Map<Class<? extends Node>, String> beginBorder = new HashMap<>(){{
        put(ObjectNode.class, "{\n");
        put(ArrayNode.class, "[\n");
        put(StringNode.class, "\"");
        put(CharacterNode.class, "'");
    }};

    private Map<Class<? extends Node>, String> endBorder = new HashMap<>(){{
        put(ObjectNode.class, "\n%offset%}");
        put(ArrayNode.class, "\n%offset%]");
        put(StringNode.class, "\"");
        put(CharacterNode.class, "'");
    }};

    private Map<Class<? extends Node>, String> firstDelimiters = new HashMap<>(){{
        put(ObjectNode.class, "");
        put(ArrayNode.class, "%offset%");
    }};

    private Map<Class<? extends Node>, String> delimiters = new HashMap<>(){{
        put(ObjectNode.class, ",\n");
        put(ArrayNode.class, ",\n%offset%");
    }};

    private String nameBorder = "\"";
    private String nameValueSeparator = ":";

    private String offsetItem = "\t";
    private int offsetCounter = 0;

    @Override
    public String getBeginBorder(Class<? extends Node> clazz) {
        return beginBorder.getOrDefault(clazz, DEFAULT_BORDER);
    }

    @Override
    public String getEndBorder(Class<? extends Node> clazz) {
        String endBorder = this.endBorder.getOrDefault(clazz, DEFAULT_BORDER);
        return endBorder.replace("%offset%", getOffset());
    }

    @Override
    public List<String> getDelimiters(Class<? extends Node> clazz, int size) {
        ArrayList<String> delimiterArray = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String delimiter = i == 0
                    ? firstDelimiters.getOrDefault(clazz, DEFAULT_DELIMITER)
                    : delimiters.getOrDefault(clazz, DEFAULT_DELIMITER);
            delimiterArray.add(delimiter.replace(OFFSET_MARKER, getOffset()));
        }

        return delimiterArray;
    }

    @Override
    public String getNameBorder() {
        return nameBorder;
    }

    @Override
    public String getNameValueSeparator() {
        return nameValueSeparator;
    }

    @Override
    public void incOffset() {
        offsetCounter++;
    }

    @Override
    public void decOffset() {
        if (offsetCounter > 0){
            offsetCounter--;
        }
    }

    @Override
    public String getOffset() {
        return String.valueOf(offsetItem.repeat(offsetCounter));
    }
}
