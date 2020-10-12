package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node;

import org.KasymbekovPN.Skeleton.custom.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.converter.Str2NodeConverter;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.SKDes2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.JsonFinder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.SKDes2NodeCharItr;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler.*;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;
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
        OldContextProcessor<Des2NodeContext> processor = createProcessor();
        Des2NodeContext context = createContext(line, processor);

        processor.handle(context);

        System.out.println(context.getNode());
    }

    private String getLine(){
        return "  { \"shield\" : \"xx \\\"lll\\\" xx\"  \"obj\" : { \"innerInt\" : 65, \"innerBool\" : fa11lse},  \"intValue\":123, \"doubleValue\" : 456.7, \"boolValue\" : true, \"charValue\" : 'x', \"strValue\" : \"hello!!!\", \"arr\" : [{\"yyy\" : 't'},123,567]}";
    }

    private Des2NodeContext createContext(String line, OldContextProcessor<Des2NodeContext> processor){

        EnumMap<Des2NodeMode, ContextIds> contextIds = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.INIT, new SKSimpleContextIds(TASK_COMMON, WRAPPER_INIT));
            put(Des2NodeMode.OBJECT, new SKSimpleContextIds(TASK_COMMON, WRAPPER_OBJECT));
            put(Des2NodeMode.ARRAY, new SKSimpleContextIds(TASK_COMMON, WRAPPER_ARRAY));
            put(Des2NodeMode.BOOLEAN, new SKSimpleContextIds(TASK_COMMON, WRAPPER_BOOLEAN));
            put(Des2NodeMode.CHARACTER, new SKSimpleContextIds(TASK_COMMON, WRAPPER_CHARACTER));
            put(Des2NodeMode.NUMBER, new SKSimpleContextIds(TASK_COMMON, WRAPPER_NUMBER));
            put(Des2NodeMode.STRING, new SKSimpleContextIds(TASK_COMMON, WRAPPER_STRING));
        }};

        SKDes2NodeCharItr iterator = new SKDes2NodeCharItr(line);

        EnumMap<Des2NodeMode, SimpleChecker<Character>> checkers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.ARRAY, new SKSimpleChecker<>('['));
            put(Des2NodeMode.BOOLEAN, new SKSimpleChecker<>('T', 't', 'F', 'f'));
            put(Des2NodeMode.CHARACTER, new SKSimpleChecker<>('\''));
            put(Des2NodeMode.OBJECT, new SKSimpleChecker<>('{'));
            put(Des2NodeMode.STRING, new SKSimpleChecker<>('"'));
            put(Des2NodeMode.NUMBER, new NumberCharacterChecker());
        }};

        EnumMap<Des2NodeMode, SimpleChecker<Character>> valueBeginCheckers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.ARRAY, new SKSimpleChecker<>('[', ','));
            put(Des2NodeMode.CHARACTER, new SKSimpleChecker<>('\''));
            put(Des2NodeMode.STRING, new SKSimpleChecker<>('"'));
        }};

        EnumMap<Des2NodeMode, SimpleChecker<Character>> valueEndCheckers = new EnumMap<>(Des2NodeMode.class) {{
            put(Des2NodeMode.NUMBER, new SKSimpleChecker<>(',', ']', '}'));
            put(Des2NodeMode.BOOLEAN, new SKSimpleChecker<>(',', ']', '}'));
            put(Des2NodeMode.CHARACTER, new SKSimpleChecker<>('\''));
            put(Des2NodeMode.STRING, new SKSimpleChecker<>('"'));
            put(Des2NodeMode.ARRAY, new SKSimpleChecker<>(']'));
            put(Des2NodeMode.OBJECT, new SKSimpleChecker<>('}'));
        }};



        JsonFinder finder = new JsonFinder(
                checkers,
                new SKSimpleChecker<>('"'),
                new SKSimpleChecker<>('"'),
                new SKSimpleChecker<>(':'),
                valueBeginCheckers,
                valueEndCheckers
        );

        return new SKDes2NodeContext(
                contextIds,
                iterator,
                finder,
                processor,
                new Str2NodeConverter()
        );
    }

    private OldContextProcessor<Des2NodeContext> createProcessor(){

        OLdContextTask<Des2NodeContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new Des2NodeInitTaskHandlerOld(WRAPPER_INIT))
                .add(new Des2NodeObjectTaskHandlerOld(WRAPPER_OBJECT))
                .add(new Des2NodeArrayTaskHandlerOld(WRAPPER_ARRAY))
                .add(new Des2NodeBooleanTaskHandlerOld(WRAPPER_BOOLEAN))
                .add(new Des2NodeCharacterTaskHandlerOld(WRAPPER_CHARACTER))
                .add(new Des2NodeNumberTaskHandlerOld(WRAPPER_NUMBER))
                .add(new Des2NodeStringTaskHandlerOld(WRAPPER_STRING));

        return new OldContextProcessor<Des2NodeContext>().add(task);
    }
}
