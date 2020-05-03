package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;

//public class NumberWritingHandler implements WritingHandler {
//<
public class NumberWritingHandler implements CollectorHandlingProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public NumberWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = this.collectorWritingProcess.getBuffer();
        this.formatter = this.collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        Number value = ((NumberNode) node).getValue();
        Class<NumberNode> clazz = NumberNode.class;
        buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
    }
}
