package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

public class BooleanWritingHandler implements CollectorProcessHandler {

    private final WritingFormatter writingFormatter;

    public BooleanWritingHandler(CollectorWritingProcess collectorWritingProcess,
                                 WritingFormatter writingFormatter) {
        collectorWritingProcess.addHandler(BooleanNode.ei(), this);

        this.writingFormatter = writingFormatter;
    }

    @Override
    public void handle(Node node) {
        if (node.isBoolean()){
            writingFormatter.addValue(node);
        }
    }
}

//<
//public class BooleanWritingHandler implements CollectorProcessHandler {
//    private final StringBuilder buffer;
//    private final Formatter formatter;
//
//    public BooleanWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
//        collectorWritingProcess.addHandler(clazz, this);
//        this.buffer = collectorWritingProcess.getBuffer();
//        this.formatter = collectorWritingProcess.getFormatter();
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isBoolean()){
//            Boolean value = ((BooleanNode) node).getValue();
//            Class<BooleanNode> clazz = BooleanNode.class;
//            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
//        }
//    }
//}