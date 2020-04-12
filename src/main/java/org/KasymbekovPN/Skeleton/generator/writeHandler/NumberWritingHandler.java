package org.KasymbekovPN.Skeleton.generator.writeHandler;

import org.KasymbekovPN.Skeleton.generator.formatter.Formatter;
import org.KasymbekovPN.Skeleton.generator.node.Node;
import org.KasymbekovPN.Skeleton.generator.node.NumberNode;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;

public class NumberWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public NumberWritingHandler(Writer writer, Class clazz) {
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
