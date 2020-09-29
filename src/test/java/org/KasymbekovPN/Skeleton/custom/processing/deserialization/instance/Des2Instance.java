package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance;

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
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2CollectionOptConverter;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SimpleContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SkeletonContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes.Des2InstanceTC0;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.SkeletonDes2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SkeletonInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonAggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonResultData;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonSimpleResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

public class Des2Instance {

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

    private static final String TASK_COMMON = "common";
    private static final String WRAPPER_HEADER = "header";
    private static final String WRAPPER_SIGNATURE = "signature";
    private static final String WRAPPER_SPECIFIC = "specific";
    private static final String WRAPPER_CUSTOM = "custom";
    private static final String WRAPPER_COLLECTION = "collection";
    private static final String WRAPPER_MAP = "map";

    @Test
    void test(){
        ClassContext classContext = createClassContext();
        classContext.attachClass(Des2InstanceTC0.class);

        ContextProcessor<ClassContext> classContextProcessor = createClassContextProcessor();
        classContextProcessor.handle(classContext);

        ObjectNode classNode = (ObjectNode) classContext.getCollector().attachNode(null);

        HashMap<String, ObjectNode> classNodes = new HashMap<>();
        classNodes.put("Des2InstanceTC0", classNode);

        ContextProcessor<InstanceContext> instanceProcessor = createInstanceProcessor();
        InstanceContext instanceContext = createInstanceContext(classNodes, instanceProcessor);

        Des2InstanceTC0 original = new Des2InstanceTC0();
        original.setIntValue(123);
        original.setFloatValue(0.25f);
        original.setDoubleValue(1.65);
        original.setBooleanValue(true);
        original.setCharValue('x');
        original.setStringObject("hello");
        original.setBooleanObject(true);
        original.setIntegerObject(654);
        original.setFloatObject(1.258f);
        original.setDoubleObject(5566.558);
        original.setCharacterObject('z');
        original.setIntSet(new HashSet<>(Arrays.asList(1,2,3)));

        instanceContext.attachInstance(original);

        instanceProcessor.handle(instanceContext);
        ObjectNode serializedData = (ObjectNode) instanceContext.getCollector().attachNode(null);

        Des2InstanceContext des2InstanceContext = createDes2InstanceContext(
                serializedData,
                classNodes
        );
        ContextProcessor<Des2InstanceContext> des2InstanceContextProcessor = createDes2InstanceContextProcessor();

        Des2InstanceTC0 restoredInstance = new Des2InstanceTC0();
        //<
        System.out.println("before : " + restoredInstance);
        //<
        des2InstanceContext.attachInstance(restoredInstance);
        des2InstanceContextProcessor.handle(des2InstanceContext);

        //<
        System.out.println("after : " + restoredInstance);
        //<
    }

    private ContextProcessor<Des2InstanceContext> createDes2InstanceContextProcessor(){
        ContextProcessor<Des2InstanceContext> processor = new ContextProcessor<>(new SkeletonAggregateResult());

        ContextTask<Des2InstanceContext> task = new ContextTask<>(new SkeletonAggregateResult());
        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper<>(
                task,
                new Des2InstanceSpecificTaskHandler(new SkeletonSimpleResult(new SkeletonResultData()), WRAPPER_SPECIFIC),
                WRAPPER_SPECIFIC
        );
        new ContextHandlerWrapper<>(
                task,
                new Des2InstanceCollectionTaskHandler(new SkeletonSimpleResult(new SkeletonResultData()), WRAPPER_COLLECTION),
                WRAPPER_COLLECTION
        );

        return processor;
    }

    private Des2InstanceContext createDes2InstanceContext(ObjectNode serializedData,
                                                          Map<String, ObjectNode> classNodes){

        SimpleContextIds simpleContextIds = new SimpleContextIds(
                TASK_COMMON,
                WRAPPER_SPECIFIC,
                WRAPPER_COLLECTION
        );

        return new SkeletonDes2InstanceContext(
                simpleContextIds,
                serializedData,
                classNodes,
                new SkeletonClassNameExtractor(),
                new AnnotationExtractor(),
                classMembersPartHandler,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classPartCollectorPath,
                new StrType2CollectionOptConverter(classMembersPartHandler)
        );
    }

    private ClassContext createClassContext(){
        SkeletonContextIds contextIds = new SkeletonContextIds();
        contextIds.addIds(
                TASK_COMMON,
                WRAPPER_SIGNATURE,
                WRAPPER_SPECIFIC,
                WRAPPER_CUSTOM,
                WRAPPER_COLLECTION,
                WRAPPER_MAP
        );

        return new SkeletonClassContext(
                contextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                null,
                new SkeletonCollector(),
                classHeaderPartHandler,
                classMembersPartHandler
        );
    }

    private SimpleChecker<Field> createCollectionTypeChecker(){
        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));

        return new CollectionTypeChecker(types, argumentTypes);
    }

    private SimpleChecker<Field> createMapTypeChecker(){
        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class));

        return new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);
    }

    private ContextProcessor<ClassContext> createClassContextProcessor(){

        SimpleChecker<Field> collectionTypeChecker = createCollectionTypeChecker();
        SimpleChecker<Field> mapTypeChecker = createMapTypeChecker();

        ContextProcessor<ClassContext> processor
                = new ContextProcessor<>(new SkeletonAggregateResult());

        ContextTask<ClassContext> task = new ContextTask<>(new SkeletonAggregateResult());

        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper<>(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_SIGNATURE
        );

        AllowedClassChecker specAllowedClassChecker = new AllowedClassChecker(
                int.class,
                float.class,
                double.class,
                boolean.class,
                char.class,
                String.class,
                Boolean.class,
                Integer.class,
                Float.class,
                Double.class,
                Character.class
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassSpecificTaskHandler(specAllowedClassChecker, WRAPPER_SPECIFIC, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_SPECIFIC
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassCustomTaskHandler(new AllowedStringChecker(), WRAPPER_CUSTOM, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_CUSTOM
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassContainerTaskHandler(collectionTypeChecker, WRAPPER_COLLECTION, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_COLLECTION
        );
        new ContextHandlerWrapper<>(
                task,
                new ClassContainerTaskHandler(mapTypeChecker, WRAPPER_MAP, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_MAP
        );

        return processor;
    }

    private ContextProcessor<InstanceContext> createInstanceProcessor(){
        ContextProcessor<InstanceContext> processor
                = new ContextProcessor<>(new SkeletonAggregateResult());


        ContextTask<InstanceContext> task = new ContextTask<>(new SkeletonAggregateResult());

        processor.add(TASK_COMMON, task);

        new ContextHandlerWrapper<>(
                task,
                new InstanceHeaderTaskHandler(new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_HEADER
        );
        new ContextHandlerWrapper<>(
                task,
                new InstanceSpecificTaskHandler(WRAPPER_SPECIFIC, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_SPECIFIC
        );
        new ContextHandlerWrapper<>(
                task,
                new InstanceCustomTaskHandler(WRAPPER_CUSTOM, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_CUSTOM
        );
        new ContextHandlerWrapper<>(
                task,
                new InstanceCollectionTaskHandler(WRAPPER_COLLECTION, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_COLLECTION
        );
        new ContextHandlerWrapper<>(
                task,
                new InstanceMapTaskHandler(WRAPPER_MAP, new SkeletonSimpleResult(new SkeletonResultData())),
                WRAPPER_MAP
        );

        return processor;
    }

    private InstanceContext createInstanceContext(Map<String, ObjectNode> classNodes,
                                                  ContextProcessor<InstanceContext> processor){
        InstanceMembersPartHandler instanceMembersPartHandler = new SkeletonInstanceMembersPartHandler();

        SkeletonContextIds contextIds = new SkeletonContextIds();
        contextIds.addIds(
                TASK_COMMON,
                WRAPPER_HEADER, WRAPPER_SPECIFIC, WRAPPER_CUSTOM, WRAPPER_COLLECTION, WRAPPER_MAP
        );

        return new SkeletonInstanceContext(
                contextIds,
                classNodes,
                new SkeletonCollector(),
                processor,
                null,
                classPartCollectorPath,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classMembersPartHandler,
                instanceMembersPartHandler,
                new SkeletonClassNameExtractor(),
                new InstanceDataMembersExtractor()
        );
    }
}
