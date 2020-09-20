package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Iterator;
import java.util.Map;

public class WritingObjectTaskHandler extends BaseContextTaskHandler {

    public WritingObjectTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        WritingContext cxt = (WritingContext) context;

        if (!cxt.getNode().is(ObjectNode.ei())){
//            success = false;
            //<
            result.setSuccess(false);
        }
    }

    @Override
    protected void doIt(Context context) {
        WritingContext cxt = (WritingContext) context;

        WritingFormatterHandler writingFormatterHandler = cxt.getWritingFormatterHandler();
        ObjectNode node = (ObjectNode) cxt.getNode();
        Map<String, Node> children = node.getChildren();

        writingFormatterHandler.addBeginBorder(node);
        Iterator<String> delimiterIterator
                = writingFormatterHandler.getDelimiters(children.keySet().size(), node).iterator();

        for (Map.Entry<String, Node> entry : children.entrySet()) {
            String name = entry.getKey();
            Node child = entry.getValue();

            writingFormatterHandler.addDelimiter(delimiterIterator);
            writingFormatterHandler.addPropertyName(node, name);

            cxt.attachNode(child);
            cxt.runProcessor();
        }

        writingFormatterHandler.addEndBorder(node);
    }
}
