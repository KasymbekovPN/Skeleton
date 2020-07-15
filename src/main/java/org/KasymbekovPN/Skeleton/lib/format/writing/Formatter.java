package org.KasymbekovPN.Skeleton.lib.format.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

import java.util.List;

//< del ??
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
