package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;

public interface WritingContext extends Context<WritingContextStateMemento> {
    WritingFormatterHandler getWritingFormatterHandler();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException;
}
