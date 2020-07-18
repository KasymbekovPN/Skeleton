//package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;
//
//import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
//import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
//import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;
//
//public class NumberWritingHandler implements CollectorProcessHandler {
//
//    private final WritingFormatter writingFormatter;
//
//    public NumberWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                WritingFormatter writingFormatter) {
//        collectorWritingProcess.addHandler(NumberNode.ei(), this);
//
//        this.writingFormatter = writingFormatter;
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isNumber()){
//            writingFormatter.addValue(node);
//        }
//    }
//}

//<
//public class NumberWritingHandler implements CollectorProcessHandler {
//
//    private final StringBuilder buffer;
//    private final Formatter formatter;
//
//    public NumberWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
//        collectorWritingProcess.addHandler(clazz, this);
//        this.buffer = collectorWritingProcess.getBuffer();
//        this.formatter = collectorWritingProcess.getFormatter();
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isNumber()){
//            Number value = ((NumberNode) node).getValue();
//            Class<NumberNode> clazz = NumberNode.class;
//            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
//        }
//    }
//}