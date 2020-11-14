package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

abstract public class WritingBaseTaskHandler extends BaseContextTaskHandler<WritingContext> {

    protected static final Logger log = LoggerFactory.getLogger(WritingBaseTaskHandler.class);

    private static final String WRONG_NODE_TYPE = "Node has wrong type - %s";

    private final Set<EntityItem> allowedEIs;

    public WritingBaseTaskHandler(String id, Set<EntityItem> allowedEIs) {
        super(id);
        this.allowedEIs = allowedEIs;
    }

    public WritingBaseTaskHandler(String id, SimpleResult simpleResult, Set<EntityItem> allowedEIs) {
        super(id, simpleResult);
        this.allowedEIs = allowedEIs;
    }

    @Override
    protected void check(WritingContext context) throws ContextStateCareTakerIsEmpty {
        WritingContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        checkNodeEI(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{} : {}", id, simpleResult.getStatus());
        }
    }

    private void checkValidation(WritingContextStateMemento memento){
        SimpleResult validationResult = memento.getValidationResult();
        if (simpleResult.isSuccess() && !validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    private void checkNodeEI(WritingContextStateMemento memento) throws ContextStateCareTakerIsEmpty {
        EntityItem ei = memento.getNode().getEI();
        if (simpleResult.isSuccess() && !allowedEIs.contains(ei)){
            simpleResult.setFailStatus(String.format(WRONG_NODE_TYPE, ei));
        }
    }
}
