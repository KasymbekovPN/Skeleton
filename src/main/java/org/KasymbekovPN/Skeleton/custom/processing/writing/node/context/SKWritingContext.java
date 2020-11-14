package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

import java.lang.reflect.InvocationTargetException;

// todo : test
public class SKWritingContext implements WritingContext {

    private final MultiContextIds<EntityItem> contextIds;
    private final WritingFormatterHandler writingFormatterHandler;
    private final ContextProcessor<WritingContext> processor;
    private final ContextStateCareTaker<WritingContextStateMemento> contextStateCareTaker;

    public SKWritingContext(MultiContextIds<EntityItem> contextIds,
                            WritingFormatterHandler writingFormatterHandler,
                            ContextProcessor<WritingContext> processor,
                            ContextStateCareTaker<WritingContextStateMemento> contextStateCareTaker) {
        this.contextIds = contextIds;
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
    public ContextIds getContextIds() throws ContextStateCareTakerIsEmpty {
        Node node = contextStateCareTaker.peek().getNode();
        contextIds.setKey(node.getEI());
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<WritingContextStateMemento> getContextStateCareTaker() {
        return contextStateCareTaker;
    }
}
