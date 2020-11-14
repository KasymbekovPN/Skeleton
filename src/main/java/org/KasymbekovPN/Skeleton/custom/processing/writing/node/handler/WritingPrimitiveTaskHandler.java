package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WritingPrimitiveTaskHandler extends WritingBaseTaskHandler {

    private static final Set<EntityItem> ALLOWED_EIS = new HashSet<>(Arrays.asList(
        NodeEI.booleanEI(),
        NodeEI.characterEI(),
        NodeEI.numberEI(),
        NodeEI.stringEI()
    ));

    public WritingPrimitiveTaskHandler(String id) {
        super(id, ALLOWED_EIS);
    }

    public WritingPrimitiveTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult, ALLOWED_EIS);
    }

    @Override
    protected void doIt(WritingContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        Node node = context.getContextStateCareTaker().peek().getNode();
        writingFormatterHandler.addValue(node);
    }
}
