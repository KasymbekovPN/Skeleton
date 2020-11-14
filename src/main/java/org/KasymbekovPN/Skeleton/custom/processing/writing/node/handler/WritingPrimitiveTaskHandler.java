package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class WritingPrimitiveTaskHandler extends BaseContextTaskHandler<WritingContext> {

    private static final Logger log = LoggerFactory.getLogger(WritingPrimitiveTaskHandler.class);

    private static final String WRONG_NODE_TYPE = "Node has wrong type - %s";
    private static final Set<EntityItem> ALLOWED_EIS = new HashSet<>(){{
        add(NodeEI.booleanEI());
        add(NodeEI.characterEI());
        add(NodeEI.numberEI());
        add(NodeEI.stringEI());
    }};

    public WritingPrimitiveTaskHandler(String id) {
        super(id);
    }

    public WritingPrimitiveTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(WritingContext context) throws ContextStateCareTakerIsEmpty {
        WritingContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        checkNodeEI(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(WritingContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        Node node = context.getContextStateCareTaker().peek().getNode();
        writingFormatterHandler.addValue(node);
    }

    private void checkValidation(WritingContextStateMemento memento){
        SimpleResult validationResult = memento.getValidationResult();
        if (simpleResult.isSuccess() && !validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    private void checkNodeEI(WritingContextStateMemento memento) throws ContextStateCareTakerIsEmpty {
        EntityItem ei = memento.getNode().getEI();
        if (simpleResult.isSuccess() && !ALLOWED_EIS.contains(ei)){
            simpleResult.setFailStatus(String.format(WRONG_NODE_TYPE, ei));
        }
    }
}
