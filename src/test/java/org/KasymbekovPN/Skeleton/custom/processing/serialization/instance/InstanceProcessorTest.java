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
import org.KasymbekovPN.Skeleton.custom.result.serialization.clazz.ClassSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.InstanceSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("InstanceProcessor. Testing of:")
public class InstanceProcessorTest {

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

    private final static String TASK_COMMON = "common";
    private final static String KIND_HEADER = "header";
    private final static String KIND_SPECIFIC = "specific";
    private final static String KIND_COLLECTION = "collection";
    private final static String KIND_MAP = "map";
    private final static String KIND_CUSTOM = "custom";
    private final static String KIND_SIGNATURE = "signature";

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

        InstanceProcessorTC0 instance = new InstanceProcessorTC0();

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

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

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());

        processor.add(TASK_COMMON, task);

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
