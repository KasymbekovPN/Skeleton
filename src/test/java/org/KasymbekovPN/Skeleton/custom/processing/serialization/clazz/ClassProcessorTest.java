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
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.classes.ClassProcessorTC0;
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
import org.junit.jupiter.api.Test;

import java.util.*;

public class ClassProcessorTest {

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

    private String customKind = "custom";
    private String specificKind = "specific";
    private String collectionKind = "collection";
    private String mapKind = "map";

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

        SkeletonClassContext context = new SkeletonClassContext(
                Arrays.asList("common"),
                Arrays.asList("signature", specificKind, customKind, collectionKind, mapKind),
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                ClassProcessorTC0.class,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());

        processor.add("common", task);

        new ContextHandlerWrapper(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new ClassSerializationResult()),
                "signature",
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassSpecificTaskHandler(new AllowedClassChecker(int.class, float.class), specificKind, new ClassSerializationResult()),
                "specific",
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassCustomTaskHandler(new AllowedStringChecker("InnerClassProcessorTC0"), customKind, new ClassSerializationResult()),
                customKind,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(collectionTypeChecker, collectionKind, new ClassSerializationResult()),
                collectionKind,
                new WrongResult()
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(mapTypeChecker, mapKind, new ClassSerializationResult()),
                mapKind,
                new WrongResult()
        );

        processor.handle(context);

        //<
        System.out.println(collector.getNode());
        //<
    }
}
