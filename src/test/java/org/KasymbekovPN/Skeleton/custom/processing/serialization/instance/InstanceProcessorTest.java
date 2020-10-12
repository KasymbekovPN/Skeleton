package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance;

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
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InnerInstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;
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

    private Pair<ClassContext, OldContextProcessor<ClassContext>> createSerializingClassContext(){
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

        OLdContextTask<ClassContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new ClassSignatureTaskHandlerOld(KIND_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandlerOld(KIND_SPECIFIC, new SKSimpleChecker<>(int.class, float.class)))
                .add(new ClassCustomTaskHandlerOld(KIND_CUSTOM, new SKSimpleChecker<>("InstanceProcessorTC0", "InnerInstanceProcessorTC0")))
                .add(new ClassContainerTaskHandlerOld(KIND_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandlerOld(KIND_MAP, mapTypeChecker));

        OldContextProcessor<ClassContext> processor
                = new OldContextProcessor<>();
        processor.add(task);

        return new MutablePair<>(context, processor);
    }


    @Test
    void test() throws Exception {

        Pair<ClassContext, OldContextProcessor<ClassContext>> pair = createSerializingClassContext();
        ClassContext classContext = pair.getLeft();
        OldContextProcessor<ClassContext> classProcessor = pair.getRight();

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.attachClass(InstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());

        classContext.attachClass(InnerInstanceProcessorTC0.class);
        classProcessor.handle(classContext);
        classNodes.put("InnerInstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());

        InstanceProcessorTC0 instance = new InstanceProcessorTC0();

        OldContextProcessor<InstanceContext> processor
                = new OldContextProcessor<>();

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

        OLdContextTask<InstanceContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new InstanceHeaderTaskHandlerOld(KIND_HEADER))
                .add(new InstanceSpecificTaskHandlerOld(KIND_SPECIFIC))
                .add(new InstanceCustomTaskHandlerOld(KIND_CUSTOM))
                .add(new InstanceCollectionTaskHandlerOld(KIND_COLLECTION))
                .add(new InstanceMapTaskHandlerOld(KIND_MAP));

        processor.add(task);
        processor.handle(instanceContext);

        //<
        System.out.println(instanceContext.getCollector().getNode());
        //<
    }
}
