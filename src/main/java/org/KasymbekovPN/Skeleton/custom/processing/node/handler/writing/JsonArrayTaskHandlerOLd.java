package org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing;

import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Iterator;
import java.util.List;

public class JsonArrayTaskHandlerOLd implements TaskHandler<Node> {

    private final WritingFormatterHandler writingFormatterHandler;

    private Result result;

    public JsonArrayTaskHandlerOLd(WritingFormatterHandler writingFormatterHandler,
                                   Result result) {
        this.writingFormatterHandler = writingFormatterHandler;
        this.result = result;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {
        List<Node> children = ((ArrayNode) object).getChildren();

        writingFormatterHandler.addBeginBorder(object);

        Iterator<String> delimiterIterator = writingFormatterHandler.getDelimiters(children.size(), object).iterator();

        for (Node child : children) {
            writingFormatterHandler.addDelimiter(delimiterIterator);
            child.apply(task);
        }
        writingFormatterHandler.addEndBorder(object);

        return result;
    }

    @Override
    public Result getResult() {
        return result;
    }
}
