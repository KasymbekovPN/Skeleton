package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;

public interface ContextStateCareTaker {
    boolean isEmpty();
    boolean push(ContextStateMemento contextStateMemento);
    ContextStateMemento pop() throws ContextStateCareTakerIsEmpty;
}
