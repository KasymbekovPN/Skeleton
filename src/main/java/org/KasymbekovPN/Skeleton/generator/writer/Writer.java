package org.KasymbekovPN.Skeleton.generator.writer;

import org.KasymbekovPN.Skeleton.generator.formatter.Formatter;
import org.KasymbekovPN.Skeleton.generator.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.generator.node.Node;

public interface Writer {
    void write(Node node);
    void addHandler(Class<? extends Node> clazz, WritingHandler handler);
    StringBuilder getBuffer();
    Formatter getFormatter();
}
