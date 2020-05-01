package org.KasymbekovPN.Skeleton.collector.formatter;

import org.KasymbekovPN.Skeleton.collector.node.Node;

import java.util.List;

public interface Formatter {
    String getBeginBorder(Class<? extends Node> clazz);
    String getEndBorder(Class<? extends Node> clazz);
    List<String> getDelimiters(Class<? extends Node> clazz, int size);

    String getNameBorder();
    String getNameValueSeparator();

    void incOffset();
    void decOffset();
    String getOffset();
}
