package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkeletonObjectWritingHandler implements CollectorProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public SkeletonObjectWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isObject()){
            Map<String, Node> children = ((SkeletonObjectNode) node).getChildren();

            Class<SkeletonObjectNode> clazz = SkeletonObjectNode.class;
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
                entry.getValue().apply(collectorWritingProcess);
            }
            formatter.decOffset();
            buffer.append(formatter.getEndBorder(clazz));
        }
    }
}
