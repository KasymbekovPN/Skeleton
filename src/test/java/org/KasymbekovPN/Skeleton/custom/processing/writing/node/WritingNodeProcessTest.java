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
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.writing.node.WritingObjectTaskHandlerResult;
import org.KasymbekovPN.Skeleton.custom.result.writing.node.WritingPrimitiveTaskHandlerResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.junit.jupiter.api.Test;

public class WritingNodeProcessTest {

    private static final String TASK_COMMON = "common";
    private static final String WRAPPER_ARRAY = "array";
    private static final String WRAPPER_OBJECT = "object";
    private static final String WRAPPER_PRIMITIVE = "primitive";

    @Test
    void test(){

    }

    private WritingContext createContext(Node node, WritingFormatterHandler writingFormatterHandler){
        return new SkeletonWritingContext(
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_ARRAY),
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_OBJECT),
                new NodeWritingContextIds(TASK_COMMON, WRAPPER_PRIMITIVE),
                writingFormatterHandler,
                node
        );
    }

    private ContextProcessor createContextProcessor(){
        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());
        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper(
                task,
                new WritingArrayTaskHandler(new WritingPrimitiveTaskHandlerResult()),
                WRAPPER_ARRAY,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new WritingObjectTaskHandler(new WritingObjectTaskHandlerResult()),
                WRAPPER_OBJECT,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new WritingPrimitiveTaskHandler(new WritingPrimitiveTaskHandlerResult()),
                WRAPPER_PRIMITIVE,
                new WrongResult()
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
                .build();

        return wfh;
    }
}
