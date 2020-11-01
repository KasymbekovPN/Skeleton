package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode;

public class SerClassNodeTest {

//    private static final String TASK_COMMON = "common";
//    private static final String KIND_SIGNATURE = "signature";
//    private static final String KIND_CUSTOM = "custom";
//    private static final String KIND_SPECIFIC = "specific";
//    private static final String KIND_COLLECTION = "collection";
//    private static final String KIND_MAP = "map";
//
//    private static final String WRAPPER_AGGR = "aggr";
//    private static final String WRAPPER_CHECK = "check";
//
//    private ClassHeaderPartHandler classHeaderPartHandler = new SKClassHeaderPartHandler(
//            "type",
//            "name",
//            "modifiers"
//    );
//
//    private ClassMembersPartHandler classMembersPartHandler = new SKClassMembersPartHandler(
//            "kind",
//            "type",
//            "className",
//            "modifiers",
//            "arguments"
//    );
//
//    private final static CollectorPath membersPartCollectorPath = new SKCollectorPath(
//            Arrays.asList("members"),
//            ObjectNode.ei()
//    );
//
//    @Test
//    void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
//        OldClassContext context = createSerContext();
//        OldContextProcessor<OldClassContext> serProcessor = createSerProcessor();
//
//        HashMap<String, ObjectNode> classNodes = new HashMap<>();
//
//        context.attachClass(InnerSerTC0.class);
//        serProcessor.handle(context);
//        classNodes.put("InnerSerTC0", (ObjectNode) context.getCollector().detachNode());
//
//        context.attachClass(SerTC0.class);
//        serProcessor.handle(context);
//        classNodes.put("SerTC0", (ObjectNode) context.getCollector().detachNode());
//
//        //<
//        System.out.println(classNodes);
//        //<
//
//        SerClassNodeContext serClassNodeContext = createSerClassNodeContext(classNodes);
//        OldContextProcessor<SerClassNodeContext> serClassNodeProcessor = createSerClassNodeProcessor();
//
//        serClassNodeProcessor.handle(serClassNodeContext);
//    }
//
//    private OldContextProcessor<OldClassContext> createSerProcessor(){
//        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
//        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
//        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);
//
//        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
//        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
//        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class));
//        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);
//
//        OLdContextTask<OldClassContext> task = new OLdContextTask<>(TASK_COMMON);
//        task.add(new ClassSignatureTaskHandlerOld(KIND_SIGNATURE, classHeaderPartHandler))
//                .add(new ClassSpecificTaskHandlerOld(KIND_SPECIFIC, new SKSimpleChecker<>(int.class, float.class)))
//                .add(new ClassCustomTaskHandlerOld(KIND_CUSTOM, new SKSimpleChecker<>("InnerSerTC0")))
//                .add(new ClassContainerTaskHandlerOld(KIND_COLLECTION, collectionTypeChecker))
//                .add(new ClassContainerTaskHandlerOld(KIND_MAP, mapTypeChecker));
//
//        return new OldContextProcessor<OldClassContext>().add(task);
//
//    }
//
//    private OldClassContext createSerContext(){
//
//        SKCollector collector = new SKCollector();
//        SKSimpleContextIds contextIds = new SKSimpleContextIds(
//                TASK_COMMON,
//                KIND_SIGNATURE,
//                KIND_SPECIFIC,
//                KIND_CUSTOM,
//                KIND_COLLECTION,
//                KIND_MAP
//        );
//        return new SKOldClassContext(
//                contextIds,
//                new AnnotationExtractor(),
//                Arrays.asList("class"),
//                Arrays.asList("members"),
//                null,
//                collector,
//                classHeaderPartHandler,
//                classMembersPartHandler
//        );
//    }
//
//    private SerClassNodeContext createSerClassNodeContext(Map<String, ObjectNode> classNodes){
//        SKSimpleContextIds contextIds = new SKSimpleContextIds(TASK_COMMON, WRAPPER_AGGR, WRAPPER_CHECK);
//        return new SKSerClassNodeContext(
//                contextIds,
//                new SKSimpleChecker<>("int", "float", "java.util.Set", "java.util.Map"),
//                classNodes,
//                membersPartCollectorPath,
//                classMembersPartHandler
//        );
//    }
//
//    private OldContextProcessor<SerClassNodeContext> createSerClassNodeProcessor(){
//
//        OLdContextTask<SerClassNodeContext> task = new OLdContextTask<>(TASK_COMMON);
//        task.add(new SerClassNodeAggregateTaskHandlerOld(WRAPPER_AGGR))
//                .add(new SerClassNodeCheckingTaskHandlerOld(WRAPPER_CHECK));
//
//        return new OldContextProcessor<SerClassNodeContext>().add(task);
//    }
}
