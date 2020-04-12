package org.KasymbekovPN.Skeleton.generator.writeHandler;

import org.KasymbekovPN.Skeleton.generator.formatter.Formatter;
import org.KasymbekovPN.Skeleton.generator.node.Node;
import org.KasymbekovPN.Skeleton.generator.node.StringNode;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;

public class StringWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public StringWritingHandler(Writer writer, Class clazz) {
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
