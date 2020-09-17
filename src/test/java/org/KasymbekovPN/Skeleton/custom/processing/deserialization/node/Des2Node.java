package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.SkeletonDes2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.JsonFinder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.ids.Des2NodeContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.SkeletonDes2NodeCharItr;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler.*;
import org.KasymbekovPN.Skeleton.custom.result.deserialization.node.*;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
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
        ContextProcessor processor = createProcessor();
        Des2NodeContext context = createContext(line, processor);


        processor.handle(context);
    }

    private String getLine(){
        return "  {\"intValue\":123}";
    }

    private Des2NodeContext createContext(String line, ContextProcessor processor){

        EnumMap<Des2NodeMode, ContextIds> contextIds = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.INIT, new Des2NodeContextIds(TASK_COMMON, WRAPPER_INIT));
            put(Des2NodeMode.OBJECT, new Des2NodeContextIds(TASK_COMMON, WRAPPER_OBJECT));
            put(Des2NodeMode.ARRAY, new Des2NodeContextIds(TASK_COMMON, WRAPPER_ARRAY));
            put(Des2NodeMode.BOOLEAN, new Des2NodeContextIds(TASK_COMMON, WRAPPER_BOOLEAN));
            put(Des2NodeMode.CHARACTER, new Des2NodeContextIds(TASK_COMMON, WRAPPER_CHARACTER));
            put(Des2NodeMode.NUMBER, new Des2NodeContextIds(TASK_COMMON, WRAPPER_NUMBER));
            put(Des2NodeMode.STRING, new Des2NodeContextIds(TASK_COMMON, WRAPPER_STRING));
        }};

        SkeletonDes2NodeCharItr iterator = new SkeletonDes2NodeCharItr(line);

        EnumMap<Des2NodeMode, SimpleChecker<Character>> checkers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.ARRAY, new AllowedCharacterChecker('['));
            put(Des2NodeMode.BOOLEAN, new AllowedCharacterChecker('T', 't', 'F', 'f'));
            put(Des2NodeMode.CHARACTER, new AllowedCharacterChecker('\''));
            put(Des2NodeMode.OBJECT, new AllowedCharacterChecker('{'));
            put(Des2NodeMode.STRING, new AllowedCharacterChecker('"'));
            put(Des2NodeMode.NUMBER, new NumberCharacterChecker());
        }};

        EnumMap<Des2NodeMode, SimpleChecker<Character>> valueBeginCheckers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.NUMBER, new NumberCharacterChecker());
        }};

        EnumMap<Des2NodeMode, SimpleChecker<Character>> valueEndCheckers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.NUMBER, new AllowedCharacterChecker(',', ']', '}'));
        }};

        JsonFinder finder = new JsonFinder(
                checkers,
                new AllowedCharacterChecker('"'),
                new AllowedCharacterChecker('"'),
                new AllowedCharacterChecker(':'),
                valueBeginCheckers,
                valueEndCheckers
        );

        return new SkeletonDes2NodeContext(
                contextIds,
                iterator,
                finder,
                processor
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
