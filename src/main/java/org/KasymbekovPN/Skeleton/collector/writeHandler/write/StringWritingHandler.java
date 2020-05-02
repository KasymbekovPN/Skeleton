package org.KasymbekovPN.Skeleton.collector.writeHandler.write;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.collector.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.collector.writer.Writer;

public class StringWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public StringWritingHandler(Writer writer, Class<? extends Node> clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = writer.getBuffer();
        this.formatter = writer.getFormatter();
    }

    @Override
    public void handle(Node node) {
        String value = ((StringNode) node).getValue();
        Class<StringNode> clazz = StringNode.class;
        buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
    }
}
