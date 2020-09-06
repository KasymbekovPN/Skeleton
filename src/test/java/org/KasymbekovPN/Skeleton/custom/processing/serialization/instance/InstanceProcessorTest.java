package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.SkeletonClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.node.InstanceDataMembersExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SkeletonClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SkeletonClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SkeletonInstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.ClassPartExistingChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InnerInstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SkeletonInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.ClassPartExistingCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.clazz.ClassSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.InstanceSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("InstanceProcessor. Testing of:")
public class InstanceProcessorTest {

    private final String SERVICE = "@service";
    private final String PATHS = "@paths";
    private final String CLASS = "@class";
    private final String MEMBERS = "@members";

    private SkeletonCollectorPath serviceClassPath
            = new SkeletonCollectorPath(Arrays.asList(SERVICE, PATHS, CLASS), ArrayNode.ei());
    private SkeletonCollectorPath serviceMembersPath
            = new SkeletonCollectorPath(Arrays.asList(SERVICE, PATHS, MEMBERS), ArrayNode.ei());

    private List<String> servicePaths = Arrays.asList(SERVICE, PATHS);
    private HashMap<String, List<String>> paths = new HashMap<>() {{
        put(CLASS, Collections.singletonList("class"));
        put(MEMBERS, Collections.singletonList("members"));
    }};

    private SkeletonCollectorPath objectPath
            = new SkeletonCollectorPath(new ArrayList<>(), ObjectNode.ei());

    private SkeletonCollectorPath stringPath
            = new SkeletonCollectorPath(new ArrayList<>(), StringNode.ei());

    private ClassHeaderPartHandler classHeaderPartHandler = new SkeletonClassHeaderPartHandler(
            "type",
            "name",
            "modifiers"
    );

    private ClassMembersPartHandler classMembersPartHandler = new SkeletonClassMembersPartHandler(
            "kind",
            "type",
            "className",
            "modifiers",
            "arguments"
    );

    private final static CollectorPath classPartCollectorPath = new SkeletonCollectorPath(
            Arrays.asList("class"),
            ObjectNode.ei()
    );

    private final static CollectorPath membersPartCollectorPath = new SkeletonCollectorPath(
            Arrays.asList("members"),
            ObjectNode.ei()
    );

//    private String containerKind = "container";
//    private String customKind = "custom";
//    private String specificKind = "specific";
    //<
    private final static String TASK_COMMON = "common";
    private final static String KIND_HEADER = "header";
    private final static String KIND_SPECIFIC = "specific";
    private final static String KIND_COLLECTION = "collection";
    private final static String KIND_MAP = "map";
    private final static String KIND_CUSTOM = "custom";
    private final static String KIND_CONTAINER = "container";
    private final static String KIND_SIGNATURE = "signature";

    //<
//    private Serializer createSerializer(Collector collector) throws Exception {
//
//        String taskName = ClassPartExistingChecker.TASK_NAME;
//        Processor<Node> processor = createSerializerNodeProcessor();
//
//        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class, float.class);
//        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("InstanceProcessorTC0", "InnerInstanceProcessorTC0");
//
//        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
//        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, InnerInstanceProcessorTC0.class));
//        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);
//
//        AllowedAnnotationTypeFilter skeletonClassAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonClass.class);
//        AllowedAnnotationTypeFilter skeletonMembersAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonMember.class);
//
//        Serializer serializer = new SkeletonSerializer.Builder(collector, TASK_COMMON)
//                .addClassHandler(new ServiceSEH(skeletonClassAnnotationFilter, servicePaths, paths))
//                .addClassHandler(new ClassSignatureSEH(skeletonClassAnnotationFilter, serviceClassPath, classHeaderPartHandler))
//                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, KIND_SPECIFIC))
//                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, KIND_CUSTOM))
//                .addMemberHandler(new ContainerMemberSEH(collectionTypeChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, KIND_CONTAINER))
//                .build();
//
//        return serializer;
//    }

    private Processor<Node> createSerializerNodeProcessor(){

        NodeTask classExistTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                classExistTask,
                new ClassPartExistingChecker(new ClassPartExistingCheckerResult(), serviceClassPath, objectPath),
                ObjectNode.ei(),
                new WrongResult()
        );

        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
        nodeProcessor.add(
                ClassPartExistingChecker.TASK_NAME,
                classExistTask
        );

        return nodeProcessor;
    }

    private Pair<ClassContext, ContextProcessor> createSerializingClassContext(){
        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, InnerInstanceProcessorTC0.class));
        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);

        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class, InnerInstanceProcessorTC0.class));
        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);

        SkeletonCollector collector = new SkeletonCollector();

        SkeletonClassContext context = new SkeletonClassContext(
                Arrays.asList(TASK_COMMON),
                Arrays.asList(KIND_SIGNATURE, KIND_SPECIFIC, KIND_CUSTOM, KIND_COLLECTION, KIND_MAP),
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                null,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());

        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new ClassSerializationResult()),
                KIND_SIGNATURE,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassSpecificTaskHandler(new AllowedClassChecker(int.class, float.class), KIND_SPECIFIC, new ClassSerializationResult()),
                KIND_SPECIFIC,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassCustomTaskHandler(new AllowedStringChecker("InstanceProcessorTC0", "InnerInstanceProcessorTC0"), KIND_CUSTOM, new ClassSerializationResult()),
                KIND_CUSTOM,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(collectionTypeChecker, KIND_COLLECTION, new ClassSerializationResult()),
                KIND_COLLECTION,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(mapTypeChecker, KIND_MAP, new ClassSerializationResult()),
                KIND_MAP,
                new WrongResult()
        );

        return new MutablePair<>(context, processor);
    }


    @Test
    void test() throws Exception {

//        Collector collector = new SkeletonCollector();
//        Serializer serializer = createSerializer(collector);
        //<
        Pair<ClassContext, ContextProcessor> pair = createSerializingClassContext();
        ClassContext classContext = pair.getLeft();
        ContextProcessor classProcessor = pair.getRight();

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.attachClass(InstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());

        classContext.attachClass(InnerInstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InnerInstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());
        //<
//        serializer.serialize(InstanceProcessorTC0.class);
//        classNodes.put("InstanceProcessorTC0", (ObjectNode) serializer.getCollector().detachNode());
//
//        serializer.serialize(InnerInstanceProcessorTC0.class);
//        classNodes.put("InnerInstanceProcessorTC0", (ObjectNode) serializer.getCollector().detachNode());

        InstanceProcessorTC0 instance = new InstanceProcessorTC0();

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());


//        SkeletonInstanceContext1 instanceData = new SkeletonInstanceContext1(
//                Arrays.asList("common"),
//                Arrays.asList("header", "specific", "container", "custom"),
//                instance,
//                classNodes,
//                new SkeletonClassNameExtractor(),
//                new InstanceDataMembersExtractorOld(serviceMembersPath, objectPath),
//                collector,
//                serviceClassPath,
//                serviceMembersPath,
//                objectPath,
//                processor
//        );
        //<
        InstanceMembersPartHandler instanceMembersPartHandler = new SkeletonInstanceMembersPartHandler();

        SkeletonInstanceContext instanceContext = new SkeletonInstanceContext(
                Arrays.asList(TASK_COMMON),
                Arrays.asList(KIND_HEADER, KIND_SPECIFIC, KIND_CUSTOM, KIND_COLLECTION, KIND_MAP),
                classNodes,
                new SkeletonCollector(),
                processor,
                instance,
                classPartCollectorPath,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classMembersPartHandler,
                instanceMembersPartHandler,
                new SkeletonClassNameExtractor(),
                new InstanceDataMembersExtractor()
        );

//        InstanceMembersPartHandler instanceMembersPartHandler = new SkeletonInstanceMembersPartHandler(instanceData);
        //<


        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());

        processor.add(TASK_COMMON, task);

        SkeletonCollectorPath instanceClassPath = new SkeletonCollectorPath(Collections.singletonList("class"), ObjectNode.ei());
        SkeletonCollectorPath instanceMembersPath = new SkeletonCollectorPath(Collections.singletonList("members"), ObjectNode.ei());

        new ContextHandlerWrapper(
                task,
                new InstanceHeaderTaskHandler(new InstanceSerializationResult()),
                KIND_HEADER,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new InstanceSpecificTaskHandler(KIND_SPECIFIC, new InstanceSerializationResult()),
                KIND_SPECIFIC,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new InstanceCustomTaskHandler(KIND_CUSTOM, new InstanceSerializationResult()),
                KIND_CUSTOM,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new InstanceCollectionTaskHandler(KIND_COLLECTION, new InstanceSerializationResult()),
                KIND_COLLECTION,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new InstanceMapTaskHandler(KIND_MAP, new InstanceSerializationResult()),
                KIND_MAP,
                new WrongResult()
        );

        processor.handle(instanceContext);

        //<
        System.out.println(instanceContext.getCollector().getNode());
        //<
    }
}
