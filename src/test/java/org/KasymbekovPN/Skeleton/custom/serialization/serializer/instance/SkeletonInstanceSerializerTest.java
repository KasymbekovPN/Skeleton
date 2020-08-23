package org.KasymbekovPN.Skeleton.custom.serialization.serializer.instance;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionInstanceChecker;
import org.KasymbekovPN.Skeleton.custom.collector.part.SkeletonClassHeaderHandler;
import org.KasymbekovPN.Skeleton.custom.collector.part.SkeletonClassMembersHandler;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.SkeletonClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.filter.annotations.AllowedAnnotationTypeFilter;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.ClassPartExistingChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.ClassPartExistingCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.InstanceSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ServiceSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.instance.handler.ClassISH;
import org.KasymbekovPN.Skeleton.custom.serialization.instance.handler.SpecificISH;
import org.KasymbekovPN.Skeleton.custom.serialization.instance.serializer.SkeletonInstanceSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.serializer.instance.classes.InstanceSerTC0;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SkeletonInstanceSerializerTest {

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

    private Serializer createSerializer(Collector collector) throws Exception {

        String taskName = ClassPartExistingChecker.TASK_NAME;
        Processor<Node> processor = createSerializerNodeProcessor();

        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class, float.class);
        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("InstanceSerTC0");

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

    @Test
    void test() throws Exception {
        SkeletonCollector collector = new SkeletonCollector();
        Serializer serializer = createSerializer(collector);

        serializer.serialize(InstanceSerTC0.class);

        ObjectNode instanceSerTC0Node = (ObjectNode) collector.detachNode();

        ClassISH classISH = new ClassISH(classHeaderHandler, serviceClassPath, objectPath, new SkeletonClassNameExtractor(), new InstanceSerializationResult());
        classISH.setNext(new SpecificISH());

        SkeletonInstanceSerializer instanceSerializer = new SkeletonInstanceSerializer(
                classHeaderHandler,
                serviceClassPath,
                objectPath,
                classISH,
                collector,
                "is",
                new SkeletonClassNameExtractor(),
                new InstanceSerializationResult()
        );
        instanceSerializer.addClassNode(instanceSerTC0Node);

        InstanceSerTC0 instanceSerTC0 = new InstanceSerTC0();
        Result result = instanceSerializer.serialize(instanceSerTC0);
        //<
//        System.out.println(result.isSuccess());
//        System.out.println(result.getStatus());
        //<

        Node instanceNode = collector.detachNode();
        System.out.println(instanceNode);
    }
}
