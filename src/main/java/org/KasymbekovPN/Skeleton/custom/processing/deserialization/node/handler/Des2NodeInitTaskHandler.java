package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class Des2NodeInitTaskHandler extends Des2NodeBaseTaskHandler {

    public Des2NodeInitTaskHandler(String id) {
        super(id);
    }

    public Des2NodeInitTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        DecrementedCharIterator iterator = context.iterator();
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        MultiChecker<EntityItem, Character> entityBeginChecker = context.getEntityBeginChecker(null);

        Optional<EntityItem> maybeEI = Optional.empty();
        while (iterator.hasNext() && maybeEI.isEmpty()){
            maybeEI = entityBeginChecker.applyByAll(iterator.next());
        }

        if (maybeEI.isPresent()){
            iterator.dec();
            memento.setKey(maybeEI.get());
            context.runProcessor();
        }
    }
}
