package org.KasymbekovPN.Skeleton.custom.serialization;

//< del
//import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
//import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
//import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
//import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SkeletonClassHeaderPartHandler;
//import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SkeletonClassMembersPartHandler;
//import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
//import org.KasymbekovPN.Skeleton.custom.filter.annotations.AllowedAnnotationTypeFilter;
//import org.KasymbekovPN.Skeleton.custom.filter.string.IgnoreStringFilter;
//import org.KasymbekovPN.Skeleton.custom.format.offset.SkeletonOffset;
//import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
//import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.ClassPartExistingChecker;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.NodeTypeChecker;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting.NodeClassNameExtractor;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonArrayTaskHandlerOLd;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonObjectTaskHandlerOld;
//import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonPrimitiveTaskHandlerOld;
//import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
//import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
//import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.ClassPartExistingCheckerResult;
//import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.NodeTypeCheckerResult;
//import org.KasymbekovPN.Skeleton.custom.result.processing.handler.extracting.NodeClassNameExtractorHandlerResult;
//import org.KasymbekovPN.Skeleton.custom.result.processing.handler.writing.json.WritingResult;
//import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
//import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
//import org.KasymbekovPN.Skeleton.custom.result.serialization.group.SerializationGroupResult;
//import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
//import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC0;
//import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC1;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ServiceSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
//import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
//import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
//import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
//import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
//import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
//import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
//import org.KasymbekovPN.Skeleton.lib.node.*;
//import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
//import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
//import org.KasymbekovPN.Skeleton.lib.result.Result;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
//import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//
//@DisplayName("SkeletonSerializerGroup: Testing of:")
//public class SkeletonSerializerGroupTest {
//
//    private final String SERVICE = "@service";
//    private final String PATHS = "@paths";
//    private final String CLASS = "@class";
//    private final String MEMBERS = "@members";
//
//    private SkeletonCollectorPath serviceClassPath
//            = new SkeletonCollectorPath(Arrays.asList(SERVICE, PATHS, CLASS), ArrayNode.ei());
//    private SkeletonCollectorPath serviceMembersPath
//            = new SkeletonCollectorPath(Arrays.asList(SERVICE, PATHS, MEMBERS), ArrayNode.ei());
//
//    private List<String> servicePaths = Arrays.asList(SERVICE, PATHS);
//    private HashMap<String, List<String>> paths = new HashMap<>() {{
//        put(CLASS, Collections.singletonList("class"));
//        put(MEMBERS, Collections.singletonList("members"));
//    }};
//
//    private SkeletonCollectorPath objectPath
//            = new SkeletonCollectorPath(new ArrayList<>(), ObjectNode.ei());
//
//    private SkeletonCollectorPath stringPath
//            = new SkeletonCollectorPath(new ArrayList<>(), StringNode.ei());
//
//    private ClassHeaderPartHandler classHeaderPartHandler = new SkeletonClassHeaderPartHandler(
//            "type",
//            "name",
//            "modifiers"
//    );
//
//    private ClassMembersPartHandler classMembersPartHandler = new SkeletonClassMembersPartHandler(
//            "kind",
//            "type",
//            "className",
//            "modifiers",
//            "arguments"
//    );
//
//    private String containerKind = "container";
//    private String customKind = "custom";
//    private String specificKind = "specific";
//
//    private WritingFormatterHandler createWFH() throws Exception {
//
//        SkeletonOffset offset = new SkeletonOffset("    ");
//
//        WritingFormatterHandler wfh = new JsonWritingFormatterHandler.Builder(offset)
//                .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter(offset))
//                .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter(offset))
//                .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter(offset))
//                .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter(offset))
//                .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter(offset))
//                .addFormatter(StringNode.ei(), new JsonStringWritingFormatter(offset))
//                .build();
//
//        return wfh;
//    }
//
//    private Serializer createSerializer(Collector collector) throws Exception {
//
//        String taskName = ClassPartExistingChecker.TASK_NAME;
//        Processor<Node> processor = createSerializerNodeProcessor();
//
//        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class, float.class);
//        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("SerializerGroupTC0", "SerializerGroupTC1");
//
//        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
//        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
//        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);
//
//        AllowedAnnotationTypeFilter skeletonClassAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonClass.class);
//        AllowedAnnotationTypeFilter skeletonMembersAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonMember.class);
//
//        Serializer serializer = new SkeletonSerializer.Builder(collector, "common")
//                .addClassHandler(new ServiceSEH(skeletonClassAnnotationFilter, servicePaths, paths))
//                .addClassHandler(new ClassSignatureSEH(skeletonClassAnnotationFilter, serviceClassPath, classHeaderPartHandler))
//                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, specificKind))
//                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, customKind))
//                .addMemberHandler(new ContainerMemberSEH(collectionTypeChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersPartHandler, containerKind))
//                .build();
//
//        return serializer;
//    }
//
//    private Processor<Node> createSerializerNodeProcessor(){
//
//        NodeTask classExistTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
//        new NodeProcessHandlerWrapper(
//                classExistTask,
//                new ClassPartExistingChecker(new ClassPartExistingCheckerResult(), serviceClassPath, objectPath),
//                ObjectNode.ei(),
//                new WrongResult()
//        );
//
//        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
//        nodeProcessor.add(
//                ClassPartExistingChecker.TASK_NAME,
//                classExistTask
//        );
//
//        return nodeProcessor;
//    }
//
//    private Processor<Node> createClassNameExtractProcessor(Collector collector){
//        NodeTask nodeClassNameExtractorTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
//        new NodeProcessHandlerWrapper(
//                nodeClassNameExtractorTask,
//                new NodeClassNameExtractor(
//                        new NodeClassNameExtractorHandlerResult(),
//                        serviceClassPath,
//                        stringPath
//                ),
//                ObjectNode.ei(),
//                new WrongResult()
//        );
//
//        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
//        nodeProcessor.add(
//                SkeletonSerializerGroup.EXTRACT_CLASS_NAME,
//                nodeClassNameExtractorTask
//        );
//
//        return nodeProcessor;
//    }
//
//    private SerializerGroup createSerializeGroup(Serializer serializer, String serializerId, Processor<Node> nodeProcessor){
//        SkeletonSerializerGroup serializerGroup = new SkeletonSerializerGroup(nodeProcessor, new SerializationGroupResult());
//        serializerGroup.attach(serializerId, serializer);
//        return serializerGroup;
//    }
//
//    private Task<Node> createCheckNodeTypeTask(SimpleChecker<String> systemTypeChecker){
//
//        NodeTask checkNodeTypeTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
//        new NodeProcessHandlerWrapper(
//                checkNodeTypeTask,
//                new NodeTypeChecker(systemTypeChecker, new NodeTypeCheckerResult(), serviceMembersPath, objectPath, classMembersPartHandler, customKind),
//                ObjectNode.ei(),
//                new WrongResult()
//        );
//
//        return checkNodeTypeTask;
//    }
//
//    private Task<Node> createWritingTask(WritingFormatterHandler wfh){
//        NodeTask nodeTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonArrayTaskHandlerOLd(wfh, new WritingResult()),
//                ArrayNode.ei(),
//                new WrongResult()
//        );
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonPrimitiveTaskHandlerOld(wfh, new WritingResult()),
//                BooleanNode.ei(),
//                new WrongResult()
//        );
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonPrimitiveTaskHandlerOld(wfh, new WritingResult()),
//                CharacterNode.ei(),
//                new WrongResult()
//        );
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonPrimitiveTaskHandlerOld(wfh, new WritingResult()),
//                NumberNode.ei(),
//                new WrongResult()
//        );
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonObjectTaskHandlerOld(
//                        wfh,
//                        new IgnoreStringFilter(SERVICE),
//                        new WritingResult()
//                ),
//                ObjectNode.ei(),
//                new WrongResult()
//        );
//        new NodeProcessHandlerWrapper(
//                nodeTask,
//                new JsonPrimitiveTaskHandlerOld(wfh, new WritingResult()),
//                StringNode.ei(),
//                new WrongResult()
//        );
//
//        return nodeTask;
//    }
//
//    @Test
//    void test() throws Exception {
//
//        Collector collector = Utils.createCollector();
//        Serializer serializer = createSerializer(collector);
//
//        Processor<Node> nodeProcessor = createClassNameExtractProcessor(collector);
//
//        String serializerId = serializer.getId();
//        SerializerGroup serializerGroup = createSerializeGroup(serializer, serializerId, nodeProcessor);
//        serializerGroup.serialize(serializerId, SerializerGroupTC0.class);
//        serializerGroup.serialize(serializerId, SerializerGroupTC1.class);
//
//        ObjectNode groupRootNode = serializerGroup.getGroupRootNode();
//
//        AllowedStringChecker systemTypeChecker = new AllowedStringChecker("int", "float", "java.util.Set");
//        Task<Node> checkNodeTypeTask = createCheckNodeTypeTask(systemTypeChecker);
//
//        groupRootNode.apply(checkNodeTypeTask);
//
//        Result checkNodeTypeResult = checkNodeTypeTask.getResult(ObjectNode.ei().toString());
//
//        if (checkNodeTypeResult.isSuccess()){
//            WritingFormatterHandler wfh = createWFH();
//            Task<Node> writingTask = createWritingTask(wfh);
//            groupRootNode.apply(writingTask);
//
//            System.out.println(wfh.getDecoder().getString());
//        }
//    }
//}
