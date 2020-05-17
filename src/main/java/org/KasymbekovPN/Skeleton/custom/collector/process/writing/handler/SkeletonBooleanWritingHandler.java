package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonBooleanNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

public class SkeletonBooleanWritingHandler implements CollectorProcessHandler {
    private final StringBuilder buffer;
    private final Formatter formatter;

    public SkeletonBooleanWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isBoolean()){
            Boolean value = ((SkeletonBooleanNode) node).getValue();
            Class<SkeletonBooleanNode> clazz = SkeletonBooleanNode.class;
            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
        }
    }
}
