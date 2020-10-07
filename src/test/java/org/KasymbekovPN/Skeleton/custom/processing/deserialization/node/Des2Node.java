package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.converter.Str2NodeConverter;
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
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonAggregateResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
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
    void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        String line = getLine();
        ContextProcessor<Des2NodeContext> processor = createProcessor();
        Des2NodeContext context = createContext(line, processor);

        processor.handle(context);

        System.out.println(context.getNode());
    }

    private String getLine(){
        return "  { \"shield\" : \"xx \\\"lll\\\" xx\"  \"obj\" : { \"innerInt\" : 65, \"innerBool\" : fa11lse},  \"intValue\":123, \"doubleValue\" : 456.7, \"boolValue\" : true, \"charValue\" : 'x', \"strValue\" : \"hello!!!\", \"arr\" : [{\"yyy\" : 't'},123,567]}";
    }

    private Des2NodeContext createContext(String line, ContextProcessor<Des2NodeContext> processor){

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
            put(Des2NodeMode.ARRAY, new AllowedCharacterChecker('[', ','));
            put(Des2NodeMode.CHARACTER, new AllowedCharacterChecker('\''));
            put(Des2NodeMode.STRING, new AllowedCharacterChecker('"'));
        }};

        EnumMap<Des2NodeMode, SimpleChecker<Character>> valueEndCheckers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.NUMBER, new AllowedCharacterChecker(',', ']', '}'));
            put(Des2NodeMode.BOOLEAN, new AllowedCharacterChecker(',', ']', '}'));
            put(Des2NodeMode.CHARACTER, new AllowedCharacterChecker('\''));
            put(Des2NodeMode.STRING, new AllowedCharacterChecker('"'));
            put(Des2NodeMode.ARRAY, new AllowedCharacterChecker(']'));
            put(Des2NodeMode.OBJECT, new AllowedCharacterChecker('}'));
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
                processor,
                new Str2NodeConverter()
        );
    }

    private ContextProcessor<Des2NodeContext> createProcessor(){

        ContextProcessor<Des2NodeContext> processor
                = new ContextProcessor<>(new SkeletonAggregateResult());

        ContextTask<Des2NodeContext> task = new ContextTask<>(new SkeletonAggregateResult());
        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper<>(
                task,
                new Des2NodeInitTaskHandler(),
                WRAPPER_INIT
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeObjectTaskHandler(),
                WRAPPER_OBJECT
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeArrayTaskHandler(),
                WRAPPER_ARRAY
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeBooleanTaskHandler(),
                WRAPPER_BOOLEAN
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeCharacterTaskHandler(),
                WRAPPER_CHARACTER
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeNumberTaskHandler(),
                WRAPPER_NUMBER
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2NodeStringTaskHandler(),
                WRAPPER_STRING
        );

        return processor;
    }
}
