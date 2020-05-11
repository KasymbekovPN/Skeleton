package org.KasymbekovPN.Skeleton.custom.format.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkeletonJsonFormatter implements Formatter {

    private static final String OFFSET_MARKER = "%offset%";
    private static final String DEFAULT_BORDER = "";
    private static final String DEFAULT_DELIMITER = "";

    private Map<Class<? extends Node>, String> beginBorder = new HashMap<>(){{
        put(SkeletonObjectNode.class, "{\n");
        put(SkeletonArrayNode.class, "[\n");
        put(SkeletonStringNodeSkeleton.class, "\"");
        put(SkeletonCharacterNodeSkeleton.class, "'");
    }};

    private Map<Class<? extends Node>, String> endBorder = new HashMap<>(){{
        put(SkeletonObjectNode.class, "\n%offset%}");
        put(SkeletonArrayNode.class, "\n%offset%]");
        put(SkeletonStringNodeSkeleton.class, "\"");
        put(SkeletonCharacterNodeSkeleton.class, "'");
    }};

    private Map<Class<? extends Node>, String> firstDelimiters = new HashMap<>(){{
        put(SkeletonObjectNode.class, "");
        put(SkeletonArrayNode.class, "%offset%");
    }};

    private Map<Class<? extends Node>, String> delimiters = new HashMap<>(){{
        put(SkeletonObjectNode.class, ",\n");
        put(SkeletonArrayNode.class, ",\n%offset%");
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
