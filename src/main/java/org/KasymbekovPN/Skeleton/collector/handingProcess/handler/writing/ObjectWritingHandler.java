package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectWritingHandler implements CollectorHandlingProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public ObjectWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isObject()){
            Map<String, Node> children = ((ObjectNode) node).getChildren();

            Class<ObjectNode> clazz = ObjectNode.class;
            Set<Map.Entry<String, Node>> entries = children.entrySet();
            List<String> delimiters = formatter.getDelimiters(clazz, entries.size());
            Iterator<String> iterator = delimiters.iterator();

            buffer.append(formatter.getBeginBorder(clazz));
            formatter.incOffset();
            for (Map.Entry<String, Node> entry : entries) {
                buffer.append(iterator.next())
                        .append(formatter.getOffset())
                        .append(formatter.getNameBorder())
                        .append(entry.getKey())
                        .append(formatter.getNameBorder())
                        .append(formatter.getNameValueSeparator());
                entry.getValue().doIt(collectorWritingProcess);
            }
            formatter.decOffset();
            buffer.append(formatter.getEndBorder(clazz));
        }
    }
}
