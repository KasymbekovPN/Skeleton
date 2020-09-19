package org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing;

//<
//public class JsonObjectTaskHandlerOld implements TaskHandler<Node> {
//
//    private final WritingFormatterHandler writingFormatterHandler;
//    private final Filter<String> propertyNameFilter;
//
//    private Result result;
//
//    public JsonObjectTaskHandlerOld(WritingFormatterHandler writingFormatterHandler,
//                                    Filter<String> propertyNameFilter,
//                                    Result result) {
//        this.writingFormatterHandler = writingFormatterHandler;
//        this.propertyNameFilter = propertyNameFilter;
//        this.result = result;
//    }
//
//    @Override
//    public Result handle(Node object, Task<Node> task) {
//        Map<String, Node> children = ((ObjectNode) object).getChildren();
//        Deque<String> filteredPropertyNames = filterPropertyName(children.keySet());
//
//        writingFormatterHandler.addBeginBorder(object);
//
//        Iterator<String> delimiterIterator
//                = writingFormatterHandler.getDelimiters(filteredPropertyNames.size(), object).iterator();
//
//        for (String filteredPropertyName : filteredPropertyNames) {
//            writingFormatterHandler.addDelimiter(delimiterIterator);
//            writingFormatterHandler.addPropertyName(object, filteredPropertyName);
//            children.get(filteredPropertyName).apply(task);
//        }
//        writingFormatterHandler.addEndBorder(object);
//
//        return result;
//    }
//
//    @Override
//    public Result getResult() {
//        return result;
//    }
//
//    private Deque<String> filterPropertyName(Set<String> rawNames) {
//        return propertyNameFilter.filter(new ArrayDeque<>(rawNames));
//    }
//}
