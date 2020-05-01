package org.KasymbekovPN.Skeleton.collector.writeHandler;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.BooleanNode;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.writer.Writer;

public class BooleanWritingHandler implements WritingHandler {
    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public BooleanWritingHandler(Writer writer, Class<? extends Node> clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = this.writer.getBuffer();
        this.formatter = this.writer.getFormatter();
    }

    @Override
    public void handle(Node node) {
        Boolean value = ((BooleanNode) node).getValue();
        Class<BooleanNode> clazz = BooleanNode.class;
        buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
    }
}
