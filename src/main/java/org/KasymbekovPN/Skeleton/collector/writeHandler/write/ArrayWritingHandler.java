package org.KasymbekovPN.Skeleton.collector.writeHandler.write;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.collector.writer.Writer;

import java.util.Iterator;
import java.util.List;

public class ArrayWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public ArrayWritingHandler(Writer writer, Class<? extends Node> clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = writer.getBuffer();
        this.formatter = writer.getFormatter();
    }

    @Override
    public void handle(Node node) {
        List<Node> children = ((ArrayNode) node).getChildren();

        Class<ArrayNode> clazz = ArrayNode.class;

        buffer.append(formatter.getBeginBorder(clazz));
        formatter.incOffset();

        List<String> delimiters = formatter.getDelimiters(clazz, children.size());
        Iterator<String> iterator = delimiters.iterator();

        for (Node child : children) {
            buffer.append(iterator.next());
            child.write(writer);
        }
        formatter.decOffset();
        buffer.append(formatter.getOffset()).append(formatter.getEndBorder(clazz));
    }
}
