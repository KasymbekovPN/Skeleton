package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Iterator;
import java.util.List;

public class WritingArrayTaskHandler extends BaseContextTaskHandler {

    public WritingArrayTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        WritingContext cxt = (WritingContext) context;

        if (!cxt.getNode().is(ArrayNode.ei())){
            success = false;
        }
    }

    @Override
    protected void doIt(Context context) {
        WritingContext cxt = (WritingContext) context;

        WritingFormatterHandler writingFormatterHandler = cxt.getWritingFormatterHandler();
        ArrayNode node = (ArrayNode) cxt.getNode();
        List<Node> children = node.getChildren();

        writingFormatterHandler.addBeginBorder(node);
        Iterator<String> delimiterIterator
                = writingFormatterHandler.getDelimiters(children.size(), node).iterator();

        for (Node child : children) {
            writingFormatterHandler.addDelimiter(delimiterIterator);

            cxt.attachNode(child);
            cxt.runProcessor();
        }

        writingFormatterHandler.addEndBorder(node);
    }
}
