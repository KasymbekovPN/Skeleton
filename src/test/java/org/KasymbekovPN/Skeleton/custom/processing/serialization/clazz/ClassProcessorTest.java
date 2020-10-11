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
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SKSimpleContextIds;
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
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
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
    void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);

        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);

        SkeletonCollector collector = new SkeletonCollector();
        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                KIND_SIGNATURE,
                KIND_SPECIFIC,
                KIND_CUSTOM,
                KIND_COLLECTION,
                KIND_MAP
        );

        SkeletonClassContext context = new SkeletonClassContext(
                contextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                ClassProcessorTC0.class,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );

        ContextTask<ClassContext> task = new ContextTask<>(TASK_COMMON);
        task.add(new ClassSignatureTaskHandler(KIND_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandler(KIND_SPECIFIC, new AllowedClassChecker(int.class, float.class)))
                .add(new ClassCustomTaskHandler(KIND_CUSTOM, new AllowedStringChecker("InnerClassProcessorTC0")))
                .add(new ClassContainerTaskHandler(KIND_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandler(KIND_MAP, mapTypeChecker));

        ContextProcessor<ClassContext> processor
                = new ContextProcessor<>();
        processor.add(task);

        processor.handle(context);

        //<
        System.out.println(collector.getNode());
        //<
    }
}
