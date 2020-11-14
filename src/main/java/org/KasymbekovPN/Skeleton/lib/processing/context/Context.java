package org.KasymbekovPN.Skeleton.lib.processing.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

public interface Context<T extends ContextStateMemento> {
    ContextIds getContextIds() throws ContextStateCareTakerIsEmpty;
    ContextStateCareTaker<T> getContextStateCareTaker();
}
