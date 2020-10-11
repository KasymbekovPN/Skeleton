package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SKContextStateCareTaker implements ContextStateCareTaker {

    private static final Logger log = LoggerFactory.getLogger(SKContextStateCareTaker.class);

    private final SimpleChecker<Class<? extends ContextStateMemento>> allowedMementoTypes;
    private final List<ContextStateMemento> mementos = new ArrayList<>();

    public SKContextStateCareTaker(SimpleChecker<Class<? extends ContextStateMemento>> allowedMementoTypes) {
        this.allowedMementoTypes = allowedMementoTypes;
    }

    @Override
    public boolean isEmpty() {
        return mementos.size() == 0;
    }

    @Override
    public boolean push(ContextStateMemento contextStateMemento) {
        if (allowedMementoTypes.check(contextStateMemento.getClass())){
            mementos.add(contextStateMemento);
            return true;
        }
        return false;
    }

    @Override
    public ContextStateMemento pop() throws ContextStateCareTakerIsEmpty {
        checkMementos();
        return mementos.remove(mementos.size() - 1);
    }

    private void checkMementos() throws ContextStateCareTakerIsEmpty {
        if (isEmpty()){
            throw new ContextStateCareTakerIsEmpty("SKContextStateCareTaker is empty");
        }
    }
}
