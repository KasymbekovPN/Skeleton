package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

import java.lang.reflect.InvocationTargetException;

public class SKWritingContext implements WritingContext {

    private final ContextIds arrayNodeContextIds;
    private final ContextIds objectNodeContextIds;
    private final ContextIds primitiveNodeContextIds;
    private final WritingFormatterHandler writingFormatterHandler;
    private final ContextProcessor<WritingContext> processor;
    private final ContextStateCareTaker<WritingContextStateMemento> contextStateCareTaker;

    public SKWritingContext(ContextIds arrayNodeContextIds,
                            ContextIds objectNodeContextIds,
                            ContextIds primitiveNodeContextIds,
                            WritingFormatterHandler writingFormatterHandler,
                            ContextProcessor<WritingContext> processor,
                            ContextStateCareTaker<WritingContextStateMemento> contextStateCareTaker) {
        this.arrayNodeContextIds = arrayNodeContextIds;
        this.objectNodeContextIds = objectNodeContextIds;
        this.primitiveNodeContextIds = primitiveNodeContextIds;
        this.writingFormatterHandler = writingFormatterHandler;
        this.processor = processor;
        this.contextStateCareTaker = contextStateCareTaker;
    }

    @Override
    public WritingFormatterHandler getWritingFormatterHandler() {
        return writingFormatterHandler;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
        processor.handle(this);
    }

    @Override
    public ContextIds getContextIds() {
        return null;
    }

    @Override
    public ContextStateCareTaker<WritingContextStateMemento> getContextStateCareTaker() {
        return contextStateCareTaker;
    }
}
