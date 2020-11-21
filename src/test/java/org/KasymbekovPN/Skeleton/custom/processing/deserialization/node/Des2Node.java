package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node;

// TODO: 21.11.2020 del
public class Des2Node {

//    private static final String TASK_COMMON = "common";
//    private static final String WRAPPER_INIT = "init";
//    private static final String WRAPPER_ARRAY = "array";
//    private static final String WRAPPER_OBJECT = "object";
//    private static final String WRAPPER_BOOLEAN = "boolean";
//    private static final String WRAPPER_CHARACTER = "character";
//    private static final String WRAPPER_NUMBER = "number";
//    private static final String WRAPPER_STRING = "string";
//
//    @Test
//    void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
//
//        String line = getLine();
//        OldContextProcessor<Des2NodeContextOld> processor = createProcessor();
//        Des2NodeContextOld context = createContext(line, processor);
//
//        processor.handle(context);
//
//        System.out.println(context.getNode());
//    }
//
//    private String getLine(){
//        return "  { \"shield\" : \"xx \\\"lll\\\" xx\"  \"obj\" : { \"innerInt\" : 65, \"innerBool\" : fa11lse},  \"intValue\":123, \"doubleValue\" : 456.7, \"boolValue\" : true, \"charValue\" : 'x', \"strValue\" : \"hello!!!\", \"arr\" : [{\"yyy\" : 't'},123,567]}";
//    }
//
//    private Des2NodeContextOld createContext(String line, OldContextProcessor<Des2NodeContextOld> processor){
//
//        EnumMap<Des2NodeModeOld, ContextIds> contextIds = new EnumMap<>(Des2NodeModeOld.class) {{
//            put(Des2NodeModeOld.INIT, new SKSimpleContextIds(TASK_COMMON, WRAPPER_INIT));
//            put(Des2NodeModeOld.OBJECT, new SKSimpleContextIds(TASK_COMMON, WRAPPER_OBJECT));
//            put(Des2NodeModeOld.ARRAY, new SKSimpleContextIds(TASK_COMMON, WRAPPER_ARRAY));
//            put(Des2NodeModeOld.BOOLEAN, new SKSimpleContextIds(TASK_COMMON, WRAPPER_BOOLEAN));
//            put(Des2NodeModeOld.CHARACTER, new SKSimpleContextIds(TASK_COMMON, WRAPPER_CHARACTER));
//            put(Des2NodeModeOld.NUMBER, new SKSimpleContextIds(TASK_COMMON, WRAPPER_NUMBER));
//            put(Des2NodeModeOld.STRING, new SKSimpleContextIds(TASK_COMMON, WRAPPER_STRING));
//        }};
//
//        SKDes2NodeCharItrOld iterator = new SKDes2NodeCharItrOld(line);
//
//        EnumMap<Des2NodeModeOld, SimpleChecker<Character>> checkers = new EnumMap<>(Des2NodeModeOld.class) {{
//            put(Des2NodeModeOld.ARRAY, new SKSimpleChecker<>('['));
//            put(Des2NodeModeOld.BOOLEAN, new SKSimpleChecker<>('T', 't', 'F', 'f'));
//            put(Des2NodeModeOld.CHARACTER, new SKSimpleChecker<>('\''));
//            put(Des2NodeModeOld.OBJECT, new SKSimpleChecker<>('{'));
//            put(Des2NodeModeOld.STRING, new SKSimpleChecker<>('"'));
//            put(Des2NodeModeOld.NUMBER, new NumberCharacterChecker());
//        }};
//
//        EnumMap<Des2NodeModeOld, SimpleChecker<Character>> valueBeginCheckers = new EnumMap<>(Des2NodeModeOld.class) {{
//            put(Des2NodeModeOld.ARRAY, new SKSimpleChecker<>('[', ','));
//            put(Des2NodeModeOld.CHARACTER, new SKSimpleChecker<>('\''));
//            put(Des2NodeModeOld.STRING, new SKSimpleChecker<>('"'));
//        }};
//
//        EnumMap<Des2NodeModeOld, SimpleChecker<Character>> valueEndCheckers = new EnumMap<>(Des2NodeModeOld.class) {{
//            put(Des2NodeModeOld.NUMBER, new SKSimpleChecker<>(',', ']', '}'));
//            put(Des2NodeModeOld.BOOLEAN, new SKSimpleChecker<>(',', ']', '}'));
//            put(Des2NodeModeOld.CHARACTER, new SKSimpleChecker<>('\''));
//            put(Des2NodeModeOld.STRING, new SKSimpleChecker<>('"'));
//            put(Des2NodeModeOld.ARRAY, new SKSimpleChecker<>(']'));
//            put(Des2NodeModeOld.OBJECT, new SKSimpleChecker<>('}'));
//        }};
//
//
//
//        JsonFinderOld finder = new JsonFinderOld(
//                checkers,
//                new SKSimpleChecker<>('"'),
//                new SKSimpleChecker<>('"'),
//                new SKSimpleChecker<>(':'),
//                valueBeginCheckers,
//                valueEndCheckers
//        );
//
//        return new SKDes2NodeContextOld(
//                contextIds,
//                iterator,
//                finder,
//                processor,
//                new Str2NodeConverter()
//        );
//    }
//
//    private OldContextProcessor<Des2NodeContextOld> createProcessor(){
//
//        OLdContextTask<Des2NodeContextOld> task = new OLdContextTask<>(TASK_COMMON);
//        task.add(new Des2NodeInitTaskHandlerOld(WRAPPER_INIT))
//                .add(new Des2NodeObjectTaskHandlerOld(WRAPPER_OBJECT))
//                .add(new Des2NodeArrayTaskHandlerOld(WRAPPER_ARRAY))
//                .add(new Des2NodeBooleanTaskHandlerOld(WRAPPER_BOOLEAN))
//                .add(new Des2NodeCharacterTaskHandlerOld(WRAPPER_CHARACTER))
//                .add(new Des2NodeNumberTaskHandlerOld(WRAPPER_NUMBER))
//                .add(new Des2NodeStringTaskHandlerOld(WRAPPER_STRING));
//
//        return new OldContextProcessor<Des2NodeContextOld>().add(task);
//    }
}
