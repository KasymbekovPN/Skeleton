package org.KasymbekovPN.Skeleton.lib.processing.context;

import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

public interface Context<T extends ContextStateMemento> {
    ContextIds getContextIds();
    ContextStateCareTaker<T> getContextStateCareTaker();
}
