package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SkeletonClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SkeletonClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SkeletonContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.classes.InnerSerTC0;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.classes.SerTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.result.serialization.clazz.ClassSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SerClassNodeTest {

    private static final String TASK_COMMON = "common";
    private static final String KIND_SIGNATURE = "signature";
    private static final String KIND_CUSTOM = "custom";
    private static final String KIND_SPECIFIC = "specific";
    private static final String KIND_COLLECTION = "collection";
    private static final String KIND_MAP = "map";

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

    @Test
    void test(){
        ClassContext context = createSerContext();
        ContextProcessor serProcessor = createSerProcessor();

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        context.attachClass(InnerSerTC0.class);
        serProcessor.handle(context);
        classNodes.put("InnerSerTC0", (ObjectNode) context.getCollector().detachNode());

        context.attachClass(SerTC0.class);
        serProcessor.handle(context);
        classNodes.put("SerTC0", (ObjectNode) context.getCollector().detachNode());

        //<
        System.out.println(classNodes);
        //<
    }

    private ContextProcessor createSerProcessor(){
        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);

        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);

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
                new ClassCustomTaskHandler(new AllowedStringChecker("InnerSerTC0"), KIND_CUSTOM, new ClassSerializationResult()),
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

        return processor;
    }

    private ClassContext createSerContext(){

        SkeletonCollector collector = new SkeletonCollector();

        SkeletonContextIds skeletonContextIds = new SkeletonContextIds();
        skeletonContextIds.addIds(
                TASK_COMMON,
                KIND_SIGNATURE,
                KIND_SPECIFIC,
                KIND_CUSTOM,
                KIND_COLLECTION,
                KIND_MAP
        );

        return new SkeletonClassContext(
                skeletonContextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                null,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );
    }
}
