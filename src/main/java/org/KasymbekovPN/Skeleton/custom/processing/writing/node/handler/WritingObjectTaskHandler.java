package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.util.Iterator;
import java.util.Map;

public class WritingObjectTaskHandler extends BaseContextTaskHandler<WritingContext> {

    public WritingObjectTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(WritingContext context, Task<WritingContext> task) {
        if (!context.getNode().is(ObjectNode.ei())){
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(WritingContext context) {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        ObjectNode node = (ObjectNode) context.getNode();
        Map<String, Node> children = node.getChildren();

        writingFormatterHandler.addBeginBorder(node);
        Iterator<String> delimiterIterator
                = writingFormatterHandler.getDelimiters(children.keySet().size(), node).iterator();

        for (Map.Entry<String, Node> entry : children.entrySet()) {
            String name = entry.getKey();
            Node child = entry.getValue();

            writingFormatterHandler.addDelimiter(delimiterIterator);
            writingFormatterHandler.addPropertyName(node, name);

            context.attachNode(child);
            context.runProcessor();
        }

        writingFormatterHandler.addEndBorder(node);
    }
}
