package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.UNodeWriting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

@DisplayName("SKWritingContext. Testing of:")
public class SKWritingContextTest {

    static private MultiContextIds<EntityItem> contextIds;
    static private ContextProcessor<WritingContext> processor;
    static private ContextStateCareTaker<WritingContextStateMemento> careTaker;
    static private WritingFormatterHandler writingFormatterHandler;
    static private WritingContext context;

    @BeforeAll
    static void init() throws Exception {
        contextIds = UNodeWriting.createContextIds();
        processor = UNodeWriting.createProcessor();
        careTaker = new SKContextStateCareTaker<>();
        writingFormatterHandler = UNodeWriting.createWritingFormatterHandler();
        context = UNodeWriting.createContext(contextIds, writingFormatterHandler, processor, careTaker);
    }

    @DisplayName("getWritingFormatterHandler method")
    @Test
    void testGetWritingFormatterHandler(){
        Assertions.assertThat(context.getWritingFormatterHandler()).isEqualTo(writingFormatterHandler);
    }

    @DisplayName("getContextIds method on Exception")
    @Test
    void testGetContextIdsOnException() throws ContextStateCareTakerIsEmpty {
        Throwable throwable = Assertions.catchThrowable(() -> {
            context.getContextIds();
        });
        Assertions.assertThat(throwable).isInstanceOf(ContextStateCareTakerIsEmpty.class);
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextIds() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        context.getContextStateCareTaker().push(
                new SKWritingContextStateMemento(new ObjectNode(null))
        );
        Assertions.assertThat(context.getContextIds()).isEqualTo(contextIds);
    }

    @DisplayName("getContextStateCareTaker method")
    @Test
    void testGetContextStateCareTaker(){
        Assertions.assertThat(context.getContextStateCareTaker()).isEqualTo(careTaker);
    }
}
