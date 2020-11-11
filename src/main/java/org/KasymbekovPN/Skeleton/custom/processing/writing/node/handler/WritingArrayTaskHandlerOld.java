package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContextOld;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class WritingArrayTaskHandlerOld extends OldBaseContextTaskHandler<WritingContextOld> {

    public WritingArrayTaskHandlerOld(String id) {
        super(id);
    }

    public WritingArrayTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(WritingContextOld context) {
        if (!context.getNode().is(ArrayNode.ei())){
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(WritingContextOld context) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {
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
