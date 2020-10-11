package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.node.InstanceDataMembersExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SKInstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InnerInstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("InstanceProcessor. Testing of:")
public class InstanceProcessorTest {

    private ClassHeaderPartHandler classHeaderPartHandler = new SKClassHeaderPartHandler(
            "type",
            "name",
            "modifiers"
    );

    private ClassMembersPartHandler classMembersPartHandler = new SKClassMembersPartHandler(
            "kind",
            "type",
            "className",
            "modifiers",
            "arguments"
    );

    private final static CollectorPath classPartCollectorPath = new SKCollectorPath(
            Arrays.asList("class"),
            ObjectNode.ei()
    );

    private final static CollectorPath membersPartCollectorPath = new SKCollectorPath(
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

    private Pair<ClassContext, ContextProcessor<ClassContext>> createSerializingClassContext(){
        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, InnerInstanceProcessorTC0.class));
        CollectionTypeChecker collectionTypeChecker = new CollectionTypeChecker(types, argumentTypes);

        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class, InnerInstanceProcessorTC0.class));
        MapTypeChecker mapTypeChecker = new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);

        SKCollector collector = new SKCollector();
        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                KIND_SIGNATURE, KIND_SPECIFIC, KIND_CUSTOM, KIND_COLLECTION, KIND_MAP
        );
        SKClassContext context = new SKClassContext(
                contextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                null,
                collector,
                classHeaderPartHandler,
                classMembersPartHandler
        );

        ContextTask<ClassContext> task = new ContextTask<>(TASK_COMMON);
        task.add(new ClassSignatureTaskHandler(KIND_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandler(KIND_SPECIFIC, new AllowedClassChecker(int.class, float.class)))
                .add(new ClassCustomTaskHandler(KIND_CUSTOM, new AllowedStringChecker("InstanceProcessorTC0", "InnerInstanceProcessorTC0")))
                .add(new ClassContainerTaskHandler(KIND_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandler(KIND_MAP, mapTypeChecker));

        ContextProcessor<ClassContext> processor
                = new ContextProcessor<>();
        processor.add(task);

        return new MutablePair<>(context, processor);
    }


    @Test
    void test() throws Exception {

        Pair<ClassContext, ContextProcessor<ClassContext>> pair = createSerializingClassContext();
        ClassContext classContext = pair.getLeft();
        ContextProcessor<ClassContext> classProcessor = pair.getRight();

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.attachClass(InstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());

        classContext.attachClass(InnerInstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InnerInstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());

        InstanceProcessorTC0 instance = new InstanceProcessorTC0();

        ContextProcessor<InstanceContext> processor
                = new ContextProcessor<>();

        InstanceMembersPartHandler instanceMembersPartHandler = new SKInstanceMembersPartHandler();
        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                KIND_HEADER, KIND_SPECIFIC, KIND_CUSTOM, KIND_COLLECTION, KIND_MAP
        );
        SKInstanceContext instanceContext = new SKInstanceContext(
                contextIds,
                classNodes,
                new SKCollector(),
                processor,
                instance,
                classPartCollectorPath,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classMembersPartHandler,
                instanceMembersPartHandler,
                new ClassNameExtractor(),
                new InstanceDataMembersExtractor()
        );

        ContextTask<InstanceContext> task = new ContextTask<>(TASK_COMMON);
        task.add(new InstanceHeaderTaskHandler(KIND_HEADER))
                .add(new InstanceSpecificTaskHandler(KIND_SPECIFIC))
                .add(new InstanceCustomTaskHandler(KIND_CUSTOM))
                .add(new InstanceCollectionTaskHandler(KIND_COLLECTION))
                .add(new InstanceMapTaskHandler(KIND_MAP));

        processor.add(task);
        processor.handle(instanceContext);

        //<
        System.out.println(instanceContext.getCollector().getNode());
        //<
    }
}
