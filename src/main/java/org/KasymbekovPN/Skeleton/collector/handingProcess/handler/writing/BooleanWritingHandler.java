package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.BooleanNode;
import org.KasymbekovPN.Skeleton.collector.node.Node;

public class BooleanWritingHandler implements CollectorHandlingProcessHandler {
    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public BooleanWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = this.collectorWritingProcess.getBuffer();
        this.formatter = this.collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isBoolean()){
            Boolean value = ((BooleanNode) node).getValue();
            Class<BooleanNode> clazz = BooleanNode.class;
            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
        }
    }
}
