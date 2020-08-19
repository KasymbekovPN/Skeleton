package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

//< del
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.filter.Filter;
//import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
//
//import java.util.*;
//
///**
// * WCPH - Writing Collector Process Handler
// */
////< !!! del
//public class JsonObjectWCPH implements WritingCollectorProcessHandler {
//
//    private final Filter<String> propertyNameFilter;
//
//    public JsonObjectWCPH(Filter<String> propertyNameFilter) {
//        this.propertyNameFilter = propertyNameFilter;
//    }
//
//    @Override
//    public void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess) {
//
//        Map<String, Node> children = ((ObjectNode) node).getChildren();
//        Deque<String> filteredPropertyNames = filterPropertyName(children.keySet());
//
//        writingFormatterHandler.addBeginBorder(node);
//
//        Iterator<String> delimiterIterator
//                = writingFormatterHandler.getDelimiters(filteredPropertyNames.size(), node).iterator();
//
//        for (String filteredPropertyName : filteredPropertyNames) {
//            writingFormatterHandler.addDelimiter(delimiterIterator);
//            writingFormatterHandler.addPropertyName(node, filteredPropertyName);
//            children.get(filteredPropertyName).apply(collectorProcess);
//        }
//        writingFormatterHandler.addEndBorder(node);
//    }
//
//    private Deque<String> filterPropertyName(Set<String> rawNames) {
//        return propertyNameFilter.filter(new ArrayDeque<>(rawNames));
//    }
//}
