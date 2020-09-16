package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.SkeletonDes2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.ids.Des2NodeContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.SkeletonDes2NodeCharItr;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler.*;
import org.KasymbekovPN.Skeleton.custom.result.deserialization.node.*;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

public class Des2Node {

    private static final String TASK_COMMON = "common";
    private static final String WRAPPER_INIT = "init";
    private static final String WRAPPER_ARRAY = "array";
    private static final String WRAPPER_OBJECT = "object";
    private static final String WRAPPER_BOOLEAN = "boolean";
    private static final String WRAPPER_CHARACTER = "character";
    private static final String WRAPPER_NUMBER = "number";
    private static final String WRAPPER_STRING = "string";

    @Test
    void test(){

        String line = getLine();
        Des2NodeContext context = createContext(line);
        ContextProcessor processor = createProcessor();

        processor.handle(context);
    }

    private String getLine(){
        return "{}";
    }

    private Des2NodeContext createContext(String line){

        EnumMap<SkeletonDes2NodeContext.Mode, ContextIds> contextIds = new EnumMap<>(SkeletonDes2NodeContext.Mode.class) {{
            put(SkeletonDes2NodeContext.Mode.INIT, new Des2NodeContextIds(TASK_COMMON, WRAPPER_INIT));
            put(SkeletonDes2NodeContext.Mode.OBJECT, new Des2NodeContextIds(TASK_COMMON, WRAPPER_OBJECT));
            put(SkeletonDes2NodeContext.Mode.ARRAY, new Des2NodeContextIds(TASK_COMMON, WRAPPER_ARRAY));
            put(SkeletonDes2NodeContext.Mode.BOOLEAN, new Des2NodeContextIds(TASK_COMMON, WRAPPER_BOOLEAN));
            put(SkeletonDes2NodeContext.Mode.CHARACTER, new Des2NodeContextIds(TASK_COMMON, WRAPPER_CHARACTER));
            put(SkeletonDes2NodeContext.Mode.NUMBER, new Des2NodeContextIds(TASK_COMMON, WRAPPER_NUMBER));
            put(SkeletonDes2NodeContext.Mode.STRING, new Des2NodeContextIds(TASK_COMMON, WRAPPER_STRING));
        }};

        SkeletonDes2NodeCharItr iterator = new SkeletonDes2NodeCharItr(line);

        return new SkeletonDes2NodeContext(
                contextIds,
                iterator
        );
    }

    private ContextProcessor createProcessor(){

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());
        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper(
                task,
                new Des2NodeInitTaskHandler(new Des2NodeInitTaskHandlerResult()),
                WRAPPER_INIT,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeObjectTaskHandler(new Des2NodeObjectTaskHandlerResult()),
                WRAPPER_OBJECT,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeArrayTaskHandler(new Des2NodeArrayTaskHandlerResult()),
                WRAPPER_ARRAY,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeBooleanTaskHandler(new Des2NodeBooleanTaskHandlerResult()),
                WRAPPER_BOOLEAN,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeCharacterTaskHandler(new Des2NodeCharacterTaskHandlerResult()),
                WRAPPER_CHARACTER,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeNumberTaskHandler(new Des2NodeNumberTaskHandlerResult()),
                WRAPPER_NUMBER,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new Des2NodeStringTaskHandler(new Des2NodeStringTaskHandlerResult()),
                WRAPPER_STRING,
                new WrongResult()
        );

        return processor;
    }
}
