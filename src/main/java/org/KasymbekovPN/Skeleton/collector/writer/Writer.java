package org.KasymbekovPN.Skeleton.collector.writer;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;

public interface Writer {
    void write(Node node);
    void addHandler(Class<? extends Node> clazz, WritingHandler handler);
    StringBuilder getBuffer();
    Formatter getFormatter();
}
