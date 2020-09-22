package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz;

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
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.classes.ClassProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonAggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonResultData;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonSimpleResult;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ClassProcessorTest {

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

        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);

        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);

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

        SkeletonClassContext context = new SkeletonClassContext(
                skeletonContextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                ClassProcessorTC0.class,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );

        ContextProcessor<ClassContext> processor
                = new ContextProcessor<>(new SkeletonAggregateResult());

        ContextTask<ClassContext> task = new ContextTask<>(new SkeletonAggregateResult());

        processor.add("common", task);

        new ContextHandlerWrapper<>(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_SIGNATURE
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassSpecificTaskHandler(new AllowedClassChecker(int.class, float.class), KIND_SPECIFIC, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_SPECIFIC
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassCustomTaskHandler(new AllowedStringChecker("InnerClassProcessorTC0"), KIND_CUSTOM, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_CUSTOM
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassContainerTaskHandler(collectionTypeChecker, KIND_COLLECTION, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_COLLECTION
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassContainerTaskHandler(mapTypeChecker, KIND_MAP, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_MAP
        );

        processor.handle(context);

        //<
        System.out.println(collector.getNode());
        //<
    }
}
