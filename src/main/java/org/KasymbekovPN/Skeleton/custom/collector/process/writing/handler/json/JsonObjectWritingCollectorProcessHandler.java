package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

public class JsonObjectWritingCollectorProcessHandler implements WritingCollectorProcessHandler {

    private final Filter<String> propertyNameFilter;

    public JsonObjectWritingCollectorProcessHandler(Filter<String> propertyNameFilter) {
        this.propertyNameFilter = propertyNameFilter;
    }

    @Override
    public void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess) {

        Map<String, Node> children = ((ObjectNode) node).getChildren();
        Deque<String> filteredPropertyNames = filterPropertyName(children.keySet());

        writingFormatterHandler.addBeginBorder(node);
        for (String filteredPropertyName : filteredPropertyNames) {
            writingFormatterHandler.addPropertyName(node, filteredPropertyName);
            children.get(filteredPropertyName).apply(collectorProcess);
        }
        writingFormatterHandler.addEndBorder(node);
    }

    private Deque<String> filterPropertyName(Set<String> rawNames){
        return propertyNameFilter.filter(new ArrayDeque<>(rawNames));
    }

    //<
//    private final StringChecker ignoredPropertyNameChecker;
//
//    public JsonObjectWritingCollectorProcessHandler(StringChecker ignoredPropertyNameChecker) {
//        this.ignoredPropertyNameChecker = ignoredPropertyNameChecker;
//    }
//
//    @Override
//    public void handle(Node node, WritingFormatter writingFormatter, CollectorProcess collectorProcess) {
//
//        writingFormatter.addBeginBorder(node);
//
//        Map<String, Node> children = ((ObjectNode) node).getChildren();
//        List<String> propertyNames = checkPropertyNames(children.keySet());
//        for (String propertyName : propertyNames) {
//            Node property = children.get(propertyName);
//            writingFormatter.addPropertyName(node, propertyName);
//            property.apply(collectorProcess);
//        }
//
//        writingFormatter.addEndBorder(node);
//    }
//
//    private List<String> checkPropertyNames(Set<String> rawPropertyNames){
//        ArrayList<String> propertyNames = new ArrayList<>();
//        for (String rawPropertyName : rawPropertyNames) {
//            if (ignoredPropertyNameChecker.check(rawPropertyName)){
//                propertyNames.add(rawPropertyName);
//            }
//        }
//
//        return propertyNames;
//    }

    //<
//    private final WritingFormatter writingFormatter;
//
//    public ObjectWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                WritingFormatter writingFormatter) {
//        collectorWritingProcess.addHandler(ObjectNode.ei(), this);
//
//        this.writingFormatter = writingFormatter;
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isObject()){
//
//            writingFormatter.addBeginBorder(node);
//
//            //< check ignored names
//
//            //< for
//                //< inner
//
//            writingFormatter.addEndBorder(node);
//        }
//    }
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
