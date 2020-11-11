package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContextOld;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

public class WritingObjectTaskHandlerOld extends OldBaseContextTaskHandler<WritingContextOld> {

    public WritingObjectTaskHandlerOld(String id) {
        super(id);
    }

    public WritingObjectTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(WritingContextOld context) {
        if (!context.getNode().is(ObjectNode.ei())){
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(WritingContextOld context) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {
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
