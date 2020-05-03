package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;

import java.util.Iterator;
import java.util.List;

//public class ArrayWritingHandler implements WritingHandler {
//<
public class ArrayWritingHandler implements CollectorHandlingProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public ArrayWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = collectorWritingProcess.getBuffer();
        this.formatter = collectorWritingProcess.getFormatter();
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
//            child.write(writer);
            //<
            child.doIt(collectorWritingProcess);
        }
        formatter.decOffset();
        buffer.append(formatter.getOffset()).append(formatter.getEndBorder(clazz));
    }
}
