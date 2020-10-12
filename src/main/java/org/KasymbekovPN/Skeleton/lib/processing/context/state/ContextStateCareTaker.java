package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;

public interface ContextStateCareTaker<T extends ContextStateMemento> {
    boolean isEmpty();
    void push(T contextStateMemento);
    T pop() throws ContextStateCareTakerIsEmpty;
    T peek() throws ContextStateCareTakerIsEmpty;
}
