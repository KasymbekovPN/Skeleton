package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SKContextStateCareTaker<T extends ContextStateMemento> implements ContextStateCareTaker<T> {

    private static final Logger log = LoggerFactory.getLogger(SKContextStateCareTaker.class);

    private final List<T> mementos = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return mementos.size() == 0;
    }

    @Override
    public void push(T contextStateMemento) throws NoSuchMethodException,
                                                   InstantiationException,
                                                   IllegalAccessException,
                                                   InvocationTargetException {
        contextStateMemento.validate();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKContextStateCareTaker<?> that = (SKContextStateCareTaker<?>) o;
        return Objects.equals(mementos, that.mementos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mementos);
    }
}
