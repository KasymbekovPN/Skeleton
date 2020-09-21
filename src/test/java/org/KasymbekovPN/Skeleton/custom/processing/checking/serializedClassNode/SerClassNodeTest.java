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
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SerClassNodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SkeletonSerClassNodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler.SerClassNodeAggregateTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler.SerClassNodeCheckingTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonAggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonResultData;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonSimpleResult;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SerClassNodeTest {

    private static final String TASK_COMMON = "common";
    private static final String KIND_SIGNATURE = "signature";
    private static final String KIND_CUSTOM = "custom";
    private static final String KIND_SPECIFIC = "specific";
    private static final String KIND_COLLECTION = "collection";
    private static final String KIND_MAP = "map";

    private static final String WRAPPER_AGGR = "aggr";
    private static final String WRAPPER_CHECK = "check";

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

    private final static CollectorPath membersPartCollectorPath = new SkeletonCollectorPath(
            Arrays.asList("members"),
            ObjectNode.ei()
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

        SerClassNodeContext serClassNodeContext = createSerClassNodeContext(classNodes);
        ContextProcessor serClassNodeProcessor = createSerClassNodeProcessor();

        serClassNodeProcessor.handle(serClassNodeContext);
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
                = new ContextProcessor(new SkeletonAggregateResult());

        ContextTask task = new ContextTask(new SkeletonAggregateResult());

        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_SIGNATURE
        );
        new ContextHandlerWrapper(
                task,
                new ClassSpecificTaskHandler(new AllowedClassChecker(int.class, float.class), KIND_SPECIFIC, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_SPECIFIC
        );
        new ContextHandlerWrapper(
                task,
                new ClassCustomTaskHandler(new AllowedStringChecker("InnerSerTC0"), KIND_CUSTOM, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_CUSTOM
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(collectionTypeChecker, KIND_COLLECTION, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_COLLECTION
        );
        new ContextHandlerWrapper(
                task,
                new ClassContainerTaskHandler(mapTypeChecker, KIND_MAP, new SkeletonSimpleResult(new SkeletonResultData())),
                KIND_MAP
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

    private SerClassNodeContext createSerClassNodeContext(Map<String, ObjectNode> classNodes){
        SkeletonContextIds contextIds = new SkeletonContextIds();
        contextIds.addIds(TASK_COMMON, WRAPPER_AGGR, WRAPPER_CHECK);
        return new SkeletonSerClassNodeContext(
                contextIds,
                new AllowedStringChecker("int", "float", "java.util.Set", "java.util.Map"),
                classNodes,
                membersPartCollectorPath,
                classMembersPartHandler
        );
    }

    private ContextProcessor createSerClassNodeProcessor(){
        ContextProcessor processor
                = new ContextProcessor(new SkeletonAggregateResult());

        ContextTask task = new ContextTask(new SkeletonAggregateResult());

        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper(
                task,
                new SerClassNodeAggregateTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_AGGR
        );
        new ContextHandlerWrapper(
                task,
                new SerClassNodeCheckingTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_CHECK
        );

        return processor;
    }
}
