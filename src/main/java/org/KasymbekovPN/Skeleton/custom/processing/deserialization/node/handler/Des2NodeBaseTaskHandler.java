package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class Des2NodeBaseTaskHandler extends BaseContextTaskHandler<Des2NodeContext> {

    protected static Logger log = LoggerFactory.getLogger(Des2NodeBaseTaskHandler.class);

    public Des2NodeBaseTaskHandler(String id) {
        super(id);
    }

    public Des2NodeBaseTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContext context) throws ContextStateCareTakerIsEmpty {
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{} : {}", id, simpleResult.getStatus());
        }
    }

    private void checkValidation(Des2NodeContextStateMemento memento){
        if (simpleResult.isSuccess() && !memento.getValidationResult().isSuccess()){
            simpleResult.setFailStatus(memento.getValidationResult().getStatus());
        }
    }
}
