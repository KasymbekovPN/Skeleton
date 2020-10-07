package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class WritingArrayTaskHandler extends BaseContextTaskHandler<WritingContext> {

    public WritingArrayTaskHandler() {
        super();
    }

    public WritingArrayTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(WritingContext context, Task<WritingContext> task) {
        if (!context.getNode().is(ArrayNode.ei())){
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(WritingContext context) throws InvocationTargetException,
                                                       NoSuchMethodException,
                                                       InstantiationException,
                                                       IllegalAccessException {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        ArrayNode node = (ArrayNode) context.getNode();
        List<Node> children = node.getChildren();

        writingFormatterHandler.addBeginBorder(node);
        Iterator<String> delimiterIterator
                = writingFormatterHandler.getDelimiters(children.size(), node).iterator();

        for (Node child : children) {
            writingFormatterHandler.addDelimiter(delimiterIterator);

            context.attachNode(child);
            context.runProcessor();
        }

        writingFormatterHandler.addEndBorder(node);
    }
}
