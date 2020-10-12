package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SKContextStateCareTaker<T extends ContextStateMemento> implements ContextStateCareTaker<T> {

    private static final Logger log = LoggerFactory.getLogger(SKContextStateCareTaker.class);

    private final List<T> mementos = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return mementos.size() == 0;
    }

    @Override
    public void push(T contextStateMemento) {
        mementos.add(contextStateMemento);
    }

    @Override
    public T pop() throws ContextStateCareTakerIsEmpty {
        checkMementos();
        return mementos.remove(mementos.size() - 1);
    }

    @Override
    public T peek() throws ContextStateCareTakerIsEmpty {
        checkMementos();
        return mementos.get(mementos.size() - 1);
    }

    private void checkMementos() throws ContextStateCareTakerIsEmpty {
        if (isEmpty()){
            throw new ContextStateCareTakerIsEmpty("SKContextStateCareTaker is empty");
        }
    }
}
