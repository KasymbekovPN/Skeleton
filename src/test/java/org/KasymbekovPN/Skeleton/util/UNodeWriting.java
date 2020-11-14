package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.SKWritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKMultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

public class UNodeWriting {

    public static MultiContextIds<EntityItem> createContextIds(){
        return new SKMultiContextIds.Builder<EntityItem>(new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.PRIMITIVE))
                .add(ArrayNode.ei(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.ARRAY))
                .add(ObjectNode.ei(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.OBJECT))
                .build();
    }

    public static WritingContext createContext(MultiContextIds<EntityItem> contextIds,
                                        WritingFormatterHandler writingFormatterHandler,
                                        ContextProcessor<WritingContext> processor,
                                        ContextStateCareTaker<WritingContextStateMemento> careTaker){
        return new SKWritingContext(
                contextIds,
                writingFormatterHandler,
                processor,
                careTaker
        );
    }

    public static ContextProcessor<WritingContext> createProcessor(){
        //<
//        OLdContextTask<WritingContextOld> task = new OLdContextTask<>(TASK_COMMON);
//        task.add(new WritingArrayTaskHandlerOld(WRAPPER_ARRAY))
//                .add(new WritingObjectTaskHandlerOld(WRAPPER_OBJECT))
//                .add(new WritingPrimitiveTaskHandlerOld(WRAPPER_PRIMITIVE));

        return new ContextProcessor<>();
    }

    public static WritingFormatterHandler createWritingFormatterHandler() throws Exception {
        SKOffset offset = new SKOffset("    ");

        return new JsonWritingFormatterHandler.Builder(offset)
                .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter(offset))
                .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter(offset))
                .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter(offset))
                .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter(offset))
                .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter(offset))
                .addFormatter(StringNode.ei(), new JsonStringWritingFormatter(offset))
                .addFormatter(InvalidNode.ei(), new JsonInvalidWritingFormatter())
                .build();
    }
}
