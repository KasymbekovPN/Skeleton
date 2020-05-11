package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

import java.util.Iterator;
import java.util.List;

public class SkeletonArrayWritingHandler implements CollectorProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public SkeletonArrayWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isArray()){
            List<Node> children = ((SkeletonArrayNode) node).getChildren();

            Class<SkeletonArrayNode> clazz = SkeletonArrayNode.class;

            buffer.append(formatter.getBeginBorder(clazz));
            formatter.incOffset();

            List<String> delimiters = formatter.getDelimiters(clazz, children.size());
            Iterator<String> iterator = delimiters.iterator();

            for (Node child : children) {
                buffer.append(iterator.next());
                child.apply(collectorWritingProcess);
            }
            formatter.decOffset();
            buffer.append(formatter.getOffset()).append(formatter.getEndBorder(clazz));
        }
    }
}
