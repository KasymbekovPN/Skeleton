package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

//< ??? move to .lib.
public class ObjectWritingHandler implements CollectorProcessHandler {

    private final WritingFormatter writingFormatter;

    public ObjectWritingHandler(CollectorWritingProcess collectorWritingProcess,
                                WritingFormatter writingFormatter) {
        collectorWritingProcess.addHandler(ObjectNode.ei(), this);

        this.writingFormatter = writingFormatter;
    }

    @Override
    public void handle(Node node) {
        if (node.isObject()){

            writingFormatter.addBeginBorder(node);

            //< check ignored names

            //< for
                //< inner

            writingFormatter.addEndBorder(node);
        }
    }
}

//<
//public class ObjectWritingHandler implements CollectorProcessHandler {
//
//    private final StringBuilder buffer;
//    private final CollectorWritingProcess collectorWritingProcess;
//    private final Formatter formatter;
//    private final StringChecker ignoredPropertyNameChecker;
//
//    public ObjectWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                Class<? extends Node> clazz) {
//        this.collectorWritingProcess = collectorWritingProcess;
//        this.collectorWritingProcess.addHandler(clazz, this);
//        this.buffer = collectorWritingProcess.getBuffer();
//        this.formatter = collectorWritingProcess.getFormatter();
//        this.ignoredPropertyNameChecker = new IgnoredPropertyNameChecker();
//    }
//
//    public ObjectWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                Class<? extends Node> clazz,
//                                StringChecker ignoredPropertyNameChecker) {
//        this.collectorWritingProcess = collectorWritingProcess;
//        this.collectorWritingProcess.addHandler(clazz, this);
//        this.buffer = collectorWritingProcess.getBuffer();
//        this.formatter = collectorWritingProcess.getFormatter();
//        this.ignoredPropertyNameChecker = ignoredPropertyNameChecker;
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isObject()){
//            Map<String, Node> children = ((ObjectNode) node).getChildren();
//
//            Class<ObjectNode> clazz = ObjectNode.class;
//            Set<Map.Entry<String, Node>> entries = children.entrySet();
//            List<String> delimiters = formatter.getDelimiters(clazz, entries.size());
//            Iterator<String> iterator = delimiters.iterator();
//
//            buffer.append(formatter.getBeginBorder(clazz));
//            formatter.incOffset();
//            for (Map.Entry<String, Node> entry : entries) {
//                String propertyName = entry.getKey();
//                if (ignoredPropertyNameChecker.check(propertyName)){
//                    buffer.append(iterator.next())
//                            .append(formatter.getOffset())
//                            .append(formatter.getNameBorder())
//                            .append(propertyName)
//                            .append(formatter.getNameBorder())
//                            .append(formatter.getNameValueSeparator());
//                    entry.getValue().apply(collectorWritingProcess);
//                }
//            }
//            formatter.decOffset();
//            buffer.append(formatter.getEndBorder(clazz));
//        }
//    }
//}
