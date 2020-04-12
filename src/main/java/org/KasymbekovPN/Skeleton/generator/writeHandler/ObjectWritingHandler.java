package org.KasymbekovPN.Skeleton.generator.writeHandler;

import org.KasymbekovPN.Skeleton.generator.formatter.Formatter;
import org.KasymbekovPN.Skeleton.generator.node.Node;
import org.KasymbekovPN.Skeleton.generator.node.ObjectNode;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectWritingHandler implements WritingHandler {

    private final StringBuilder buffer;
    private final Writer writer;
    private final Formatter formatter;

    public ObjectWritingHandler(Writer writer, Class clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = writer.getBuffer();
        this.formatter = writer.getFormatter();
    }

    @Override
    public void handle(Node node) {
        Map<String, Node> children = ((ObjectNode) node).getChildren();

        Class<ObjectNode> clazz = ObjectNode.class;
        Set<Map.Entry<String, Node>> entries = children.entrySet();
        List<String> delimiters = formatter.getDelimiters(clazz, entries.size());
        Iterator<String> iterator = delimiters.iterator();

        buffer.append(formatter.getBeginBorder(clazz));
        for (Map.Entry<String, Node> entry : entries) {
            buffer.append(iterator.next())
                    .append(formatter.getNameBorder())
                    .append(entry.getKey())
                    .append(formatter.getNameBorder())
                    .append(formatter.getNameValueSeparator());
            entry.getValue().write(writer);
        }
        buffer.append(formatter.getEndBorder(clazz));
    }
}
