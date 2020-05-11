package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonNumberNodeSkeleton;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;

public class SkeletonNumberWritingHandler implements CollectorProcessHandler {

    private final StringBuilder buffer;
    private final Formatter formatter;

    public SkeletonNumberWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isNumber()){
            Number value = ((SkeletonNumberNodeSkeleton) node).getValue();
            Class<SkeletonNumberNodeSkeleton> clazz = SkeletonNumberNodeSkeleton.class;
            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
        }
    }
}
