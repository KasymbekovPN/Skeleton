package org.KasymbekovPN.Skeleton.collector.writeHandler.write;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.collector.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.collector.writer.Writer;

public class NumberWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public NumberWritingHandler(Writer writer, Class<? extends Node> clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = this.writer.getBuffer();
        this.formatter = this.writer.getFormatter();
    }

    @Override
    public void handle(Node node) {
        Number value = ((NumberNode) node).getValue();
        Class<NumberNode> clazz = NumberNode.class;
        buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
    }
}
