package org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing;

//<
//public class JsonArrayTaskHandlerOLd implements TaskHandler<Node> {
//
//    private final WritingFormatterHandler writingFormatterHandler;
//
//    private Result result;
//
//    public JsonArrayTaskHandlerOLd(WritingFormatterHandler writingFormatterHandler,
//                                   Result result) {
//        this.writingFormatterHandler = writingFormatterHandler;
//        this.result = result;
//    }
//
//    @Override
//    public Result handle(Node object, Task<Node> task) {
//        List<Node> children = ((ArrayNode) object).getChildren();
//
//        writingFormatterHandler.addBeginBorder(object);
//
//        Iterator<String> delimiterIterator = writingFormatterHandler.getDelimiters(children.size(), object).iterator();
//
//        for (Node child : children) {
//            writingFormatterHandler.addDelimiter(delimiterIterator);
//            child.apply(task);
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
//}
