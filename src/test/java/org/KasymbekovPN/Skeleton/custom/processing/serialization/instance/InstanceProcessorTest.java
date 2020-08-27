package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionInstanceChecker;
import org.KasymbekovPN.Skeleton.custom.collector.part.SkeletonClassHeaderHandler;
import org.KasymbekovPN.Skeleton.custom.collector.part.SkeletonClassMembersHandler;
import org.KasymbekovPN.Skeleton.custom.collector.part.SkeletonInstanceMembersHandler;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.SkeletonClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.node.InstanceDataMembersExtractor;
import org.KasymbekovPN.Skeleton.custom.filter.annotations.AllowedAnnotationTypeFilter;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.ClassPartExistingChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.SkeletonInstanceData;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.InstanceHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificMemberTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.processor.InstanceProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.task.InstanceTask;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.ClassPartExistingCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.InstanceSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ServiceSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
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

    private ClassHeaderHandler classHeaderHandler = new SkeletonClassHeaderHandler(
            "type",
            "name",
            "modifiers"
    );

    private ClassMembersHandler classMembersHandler = new SkeletonClassMembersHandler(
            "kind",
            "type",
            "className",
            "modifiers",
            "arguments"
    );

    private String containerKind = "container";
    private String customKind = "custom";
    private String specificKind = "specific";

    private InstanceMembersHandler instanceMembersHandler = new SkeletonInstanceMembersHandler();

    private Serializer createSerializer(Collector collector) throws Exception {

        String taskName = ClassPartExistingChecker.TASK_NAME;
        Processor<Node> processor = createSerializerNodeProcessor();

        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class, float.class);
        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("InstanceProcessorTC0");

        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
        CollectionInstanceChecker collectionInstanceChecker = new CollectionInstanceChecker(types, argumentTypes);

        AllowedAnnotationTypeFilter skeletonClassAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonClass.class);
        AllowedAnnotationTypeFilter skeletonMembersAnnotationFilter = new AllowedAnnotationTypeFilter(SkeletonMember.class);

        Serializer serializer = new SkeletonSerializer.Builder(collector, "common")
                .addClassHandler(new ServiceSEH(skeletonClassAnnotationFilter, servicePaths, paths))
                .addClassHandler(new ClassSignatureSEH(skeletonClassAnnotationFilter, serviceClassPath, classHeaderHandler))
                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersHandler, specificKind))
                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersHandler, customKind))
                .addMemberHandler(new ContainerMemberSEH(collectionInstanceChecker, skeletonMembersAnnotationFilter, processor, taskName, serviceMembersPath, classMembersHandler, containerKind))
                .build();

        return serializer;
    }

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

    @Test
    void test() throws Exception {

        InstanceTask task = new InstanceTask(new InstanceTaskResult(new WrongResult()), new WrongResult());
        new InstanceHandlerWrapper(
                task,
                new InstanceHeaderTaskHandler(serviceClassPath, objectPath, classHeaderHandler, new InstanceSerializationResult()),
                "header",
                new WrongResult()
        );
        new InstanceHandlerWrapper(
                task,
                new InstanceSpecificMemberTaskHandler(specificKind, serviceMembersPath, objectPath, classMembersHandler, instanceMembersHandler, new InstanceSerializationResult()),
                "specific",
                new WrongResult()
        );

        InstanceProcessor processor
                = new InstanceProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());
        processor.add("common", task);

        Collector collector = new SkeletonCollector();
        Serializer serializer = createSerializer(collector);

        serializer.serialize(InstanceProcessorTC0.class);

        //<
//        System.out.println(serializer.getCollector().getNode());
        //<

        InstanceProcessorTC0 instance = new InstanceProcessorTC0();

        HashMap<String, ObjectNode> classNodes = new HashMap<>();
        classNodes.put("InstanceProcessorTC0", (ObjectNode) serializer.getCollector().detachNode());

        SkeletonInstanceData instanceData = new SkeletonInstanceData(
                Arrays.asList("common"),
                Arrays.asList("header", "specific"),
                instance,
                classNodes,
                new SkeletonClassNameExtractor(),
                new InstanceDataMembersExtractor(serviceMembersPath, objectPath),
                collector
        );
        processor.handle(instanceData);

        //<
        System.out.println(collector.getNode());
        //<
    }
}
