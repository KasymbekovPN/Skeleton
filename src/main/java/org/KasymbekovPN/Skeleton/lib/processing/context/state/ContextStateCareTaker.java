package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;

import java.lang.reflect.InvocationTargetException;

public interface ContextStateCareTaker<T extends ContextStateMemento> {
    boolean isEmpty();
    void push(T contextStateMemento) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    T pop() throws ContextStateCareTakerIsEmpty;
    T peek() throws ContextStateCareTakerIsEmpty;
}
