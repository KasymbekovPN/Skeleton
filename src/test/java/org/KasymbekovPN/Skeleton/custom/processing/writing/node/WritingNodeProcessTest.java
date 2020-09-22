package org.KasymbekovPN.Skeleton.custom.processing.writing.node;

import org.KasymbekovPN.Skeleton.custom.format.offset.SkeletonOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.NodeWritingContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.SkeletonWritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingArrayTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingObjectTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler.WritingPrimitiveTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonAggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonResultData;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonSimpleResult;
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
        SkeletonCollector collector = new SkeletonCollector();

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
        return new SkeletonWritingContext(
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_ARRAY),
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_OBJECT),
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_PRIMITIVE),
                writingFormatterHandler,
                contextProcessor,
                node
        );
    }

    private ContextProcessor<WritingContext> createContextProcessor(){
        ContextProcessor<WritingContext> processor
                = new ContextProcessor<>(new SkeletonAggregateResult());

        ContextTask<WritingContext> task = new ContextTask<>(new SkeletonAggregateResult());
        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper<>(
                task,
                new WritingArrayTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_ARRAY
        );
        new ContextHandlerWrapper<>(
                task,
                new WritingObjectTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_OBJECT
        );
        new ContextHandlerWrapper<>(
                task,
                new WritingPrimitiveTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_PRIMITIVE
        );

        return processor;
    }

    private WritingFormatterHandler createWFH() throws Exception {

        SkeletonOffset offset = new SkeletonOffset("    ");

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
