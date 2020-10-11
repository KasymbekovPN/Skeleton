package org.KasymbekovPN.Skeleton.custom.processing.writing.node;

import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.SKWritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingArrayTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingObjectTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingPrimitiveTaskHandler;
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
        ContextProcessor<WritingContext> processor = createContextProcessor();
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
                                         ContextProcessor<WritingContext> contextProcessor){
        return new SKWritingContext(
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_ARRAY),
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_OBJECT),
                new SKSimpleContextIds(TASK_COMMON, WRAPPER_PRIMITIVE),
                writingFormatterHandler,
                contextProcessor,
                node
        );
    }

    private ContextProcessor<WritingContext> createContextProcessor(){


        ContextTask<WritingContext> task = new ContextTask<>(TASK_COMMON);
        task.add(new WritingArrayTaskHandler(WRAPPER_ARRAY))
                .add(new WritingObjectTaskHandler(WRAPPER_OBJECT))
                .add(new WritingPrimitiveTaskHandler(WRAPPER_PRIMITIVE));

        return new ContextProcessor<WritingContext>().add(task);
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
