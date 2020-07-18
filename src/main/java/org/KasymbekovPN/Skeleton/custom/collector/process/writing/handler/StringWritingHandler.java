//package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;
//
//import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
//import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
//import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;
//
//public class StringWritingHandler implements CollectorProcessHandler {
//
//    private final WritingFormatter writingFormatter;
//
//    public StringWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                WritingFormatter writingFormatter) {
//        collectorWritingProcess.addHandler(StringNode.ei(), this);
//
//        this.writingFormatter = writingFormatter;
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isString()){
//            writingFormatter.addValue(node);
//        }
//    }
//}
//
////<
////public class StringWritingHandler implements CollectorProcessHandler {
////
////    private final StringBuilder buffer;
////    private final CollectorWritingProcess collectorWritingProcess;
////    private final Formatter formatter;
////
////    public StringWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
////        this.collectorWritingProcess = collectorWritingProcess;
////        this.collectorWritingProcess.addHandler(clazz, this);
////        this.buffer = collectorWritingProcess.getBuffer();
////        this.formatter = collectorWritingProcess.getFormatter();
////    }
////
////    @Override
////    public void handle(Node node) {
////        if (node.isString()){
////            String value = ((StringNode) node).getValue();
////            Class<StringNode> clazz = StringNode.class;
////            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
////        }
////    }
////}