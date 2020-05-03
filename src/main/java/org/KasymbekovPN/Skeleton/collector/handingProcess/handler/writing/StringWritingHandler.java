package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;

//public class StringWritingHandler implements WritingHandler {
//<
public class StringWritingHandler implements CollectorHandlingProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public StringWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        String value = ((StringNode) node).getValue();
        Class<StringNode> clazz = StringNode.class;
        buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
    }
}
