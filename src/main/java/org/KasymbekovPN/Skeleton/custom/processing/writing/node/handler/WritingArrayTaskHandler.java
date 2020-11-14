package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class WritingArrayTaskHandler extends WritingBaseTaskHandler {

    private static final Set<EntityItem> ALLOWED_EIS = new HashSet<>(Collections.singletonList(NodeEI.arrayEI()));

    public WritingArrayTaskHandler(String id) {
        super(id, ALLOWED_EIS);
    }

    public WritingArrayTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult, ALLOWED_EIS);
    }

    @Override
    protected void doIt(WritingContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        ArrayNode node = (ArrayNode) context.getContextStateCareTaker().peek().getNode();
        List<Node> children = node.getChildren();

        writingFormatterHandler.addBeginBorder(node);
        Iterator<String> delimiterIterator
                = writingFormatterHandler.getDelimiters(children.size(), node).iterator();

        for (Node child : children) {
            writingFormatterHandler.addDelimiter(delimiterIterator);

            context.getContextStateCareTaker().push(new SKWritingContextStateMemento(child));
            context.runProcessor();
        }

        writingFormatterHandler.addEndBorder(node);
    }
}
