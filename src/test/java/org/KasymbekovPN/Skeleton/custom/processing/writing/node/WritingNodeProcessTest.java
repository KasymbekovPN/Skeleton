package org.KasymbekovPN.Skeleton.custom.processing.writing.node;

import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.SKWritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingArrayTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingObjectTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingPrimitiveTaskHandlerOld;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.junit.jupiter.api.Test;

public class WritingNodeProcessTest {

    private static final String TASK_COMMON = "common";
    private static final String WRAPPER_ARRAY = "array";
    private static final String WRAPPER_OBJECT = "object";
    private static final String WRAPPER_PRIMITIVE = "primitive";

    @Test
    void test() throws Exception {
        Node node = createNode();
        WritingFormatterHandler wfh = createWFH();
        OldContextProcessor<WritingContext> processor = createContextProcessor();
        WritingContext context = createContext(node, wfh, processor);

        processor.handle(context);

        //<
        System.out.println(wfh.getDecoder().getString());
        //<
    }

    private Node createNode(){
        SKCollector collector = new SKCollector();

        collector.addProperty("numberValue", 12.34);
        collector.addProperty("stringValue", "hello");
        collector.addProperty("booleanValue", true);
        collector.addProperty("charValue", 'x');

        collector.beginObject("objectValue");
        collector.addProperty("innerInt", 12);
        collector.end();

        collector.beginArray("array");
        collector.beginObject();
        collector.addProperty("x", 123);
        collector.end();
        collector.addProperty( "world");
        collector.addProperty(12);
        collector.addProperty(false);
        collector.addProperty('c');
        collector.end();

        collector.reset();
        return collector.getNode();
    }

    private WritingContext createContext(Node node,
                                         WritingFormatterHandler writingFormatterHandler,
                                         OldContextProcessor<WritingContext> oldContextProcessor){
        return new SKWritingContext(
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_ARRAY),
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_OBJECT),
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_PRIMITIVE),
                writingFormatterHandler,
                oldContextProcessor,
                node
        );
    }

    private OldContextProcessor<WritingContext> createContextProcessor(){


        OLdContextTask<WritingContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new WritingArrayTaskHandlerOld(WRAPPER_ARRAY))
                .add(new WritingObjectTaskHandlerOld(WRAPPER_OBJECT))
                .add(new WritingPrimitiveTaskHandlerOld(WRAPPER_PRIMITIVE));

        return new OldContextProcessor<WritingContext>().add(task);
    }

    private WritingFormatterHandler createWFH() throws Exception {

        SKOffset offset = new SKOffset("    ");

        WritingFormatterHandler wfh = new JsonWritingFormatterHandler.Builder(offset)
                .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter(offset))
                .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter(offset))
                .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter(offset))
                .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter(offset))
                .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter(offset))
                .addFormatter(StringNode.ei(), new JsonStringWritingFormatter(offset))
                .addFormatter(InvalidNode.ei(), new JsonInvalidWritingFormatter())
                .build();

        return wfh;
    }
}
