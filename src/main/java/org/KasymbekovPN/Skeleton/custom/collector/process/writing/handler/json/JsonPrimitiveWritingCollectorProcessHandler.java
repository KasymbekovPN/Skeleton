package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

public class JsonPrimitiveWritingCollectorProcessHandler implements WritingCollectorProcessHandler {

    @Override
    public void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess) {
        writingFormatterHandler.addValue(node);
    }

    //<
//    private final WritingFormatter writingFormatter;
//
//    public JsonPrimitiveWritingHandler(WritingFormatter writingFormatter) {
//        this.writingFormatter = writingFormatter;
//    }
//
//    @Override
//    public void handle(Node node) {
//        writingFormatter.addValue(node);
//    }
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