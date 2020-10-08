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
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2CollectionOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2MapOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SimpleContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.SkeletonContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes.Des2InstanceInnerTC0;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes.Des2InstanceTC0;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.SkeletonDes2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceMapTaskHandler;
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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        ContextProcessor<ClassContext> classContextProcessor = createClassContextProcessor();

        ClassContext classContext = createClassContext();
        classContext.attachClass(Des2InstanceTC0.class);
        classContextProcessor.handle(classContext);

//        classNodes.put("Des2InstanceTC0", (ObjectNode) classContext.getCollector().attachNode(new ObjectNode(null)));
        //<
        ObjectNode objectNode1 = new ObjectNode(null);
        classNodes.put("Des2InstanceTC0", (ObjectNode) classContext.getCollector().attach(objectNode1, objectNode1).getLeft());

        classContext.attachClass(Des2InstanceInnerTC0.class);
        classContextProcessor.handle(classContext);
        classNodes.put("Des2InstanceInnerTC0", (ObjectNode) classContext.getCollector().getNode());

        //<
        System.out.println(classNodes);

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
        original.setIntList(Arrays.asList(4, 5, 6));
        original.setFloatSet(new HashSet<>(Arrays.asList(1.1f, 1.2f)));
        original.setFloatList(Arrays.asList(1.3f, 1.4f));
        original.setDoubleSet(new HashSet<>(Arrays.asList(100.1, 100.2)));
        original.setDoubleList(Arrays.asList(100.3, 100.4));
        original.setBooleanSet(new HashSet<>(Arrays.asList(false, true)));
        original.setBooleanList(Arrays.asList(true, false, true));
        original.setCharacterSet(new HashSet<>(Arrays.asList('a', 'b')));
        original.setCharacterList(Arrays.asList('c', 'd'));
        original.setStringSet(new HashSet<>(Arrays.asList("hello", "world")));
        original.setStringList(Arrays.asList("aaa", "bbb"));

        Des2InstanceInnerTC0 custom = new Des2InstanceInnerTC0();
        custom.setIntValue(456);
        original.setCustom(custom);

        Des2InstanceInnerTC0 custom2 = new Des2InstanceInnerTC0();
        custom2.setIntValue(789);
        original.setCustom2(custom2);

        original.setIntValue2(963);

        Des2InstanceInnerTC0 setCustom1 = new Des2InstanceInnerTC0();
        setCustom1.setIntValue(101);
        Des2InstanceInnerTC0 setCustom2 = new Des2InstanceInnerTC0();
        setCustom2.setIntValue(102);
        Des2InstanceInnerTC0 setCustom3 = new Des2InstanceInnerTC0();
        setCustom3.setIntValue(103);
        original.setCustomSet(new HashSet<>(Arrays.asList(
                setCustom1,
                setCustom2,
                setCustom3
        )));

        Des2InstanceInnerTC0 listCustom1 = new Des2InstanceInnerTC0();
        listCustom1.setIntValue(1001);
        Des2InstanceInnerTC0 listCustom2 = new Des2InstanceInnerTC0();
        listCustom2.setIntValue(1002);
        original.setCustomList(Arrays.asList(listCustom1, listCustom2));

        HashMap<Integer, String> strByIntMap = new HashMap<>() {{
            put(12, "Hello");
            put(13, "world");
        }};
        original.setStringByIntegerMap(strByIntMap);

        Des2InstanceInnerTC0 mapCustom1 = new Des2InstanceInnerTC0();
        mapCustom1.setIntValue(777);
        Des2InstanceInnerTC0 mapCustom2 = new Des2InstanceInnerTC0();
        mapCustom2.setIntValue(888);
        HashMap<String, Des2InstanceInnerTC0> customMap = new HashMap<>() {{
            put("first", mapCustom1);
            put("second", mapCustom2);
        }};
        original.setCustomByStringMap(customMap);

        //<
        System.out.println("original : " + original);

        instanceContext.attachInstance(original);

        instanceProcessor.handle(instanceContext);
//        ObjectNode serializedData = (ObjectNode) instanceContext.getCollector().attachNode(null);
        //<
        ObjectNode serializedData = (ObjectNode) instanceContext.getCollector().getNode();

        //<
        System.out.println("SD : " + serializedData);
        //<

        ContextProcessor<Des2InstanceContext> des2InstanceContextProcessor = createDes2InstanceContextProcessor();
        Des2InstanceContext des2InstanceContext = createDes2InstanceContext(
                classNodes,
                des2InstanceContextProcessor
        );

        Des2InstanceTC0 restoredInstance = new Des2InstanceTC0();
        //<
        System.out.println("before : " + restoredInstance);
        //<
//        des2InstanceContext.attachInstance(restoredInstance);
        //<
        des2InstanceContext.push(restoredInstance, serializedData);
        des2InstanceContextProcessor.handle(des2InstanceContext);

        //<
        System.out.println("after : " + restoredInstance);
        //<
    }

    private ContextProcessor<Des2InstanceContext> createDes2InstanceContextProcessor(){

        ContextTask<Des2InstanceContext> task = new ContextTask<>(TASK_COMMON, new SkeletonAggregateResult());
        task.add(new Des2InstanceSpecificTaskHandler(WRAPPER_SPECIFIC))
                .add(new Des2InstanceCollectionTaskHandler(WRAPPER_COLLECTION))
                .add(new Des2InstanceCustomTaskHandler(WRAPPER_CUSTOM))
                .add(new Des2InstanceMapTaskHandler(WRAPPER_MAP));

        return new ContextProcessor<Des2InstanceContext>(new SkeletonAggregateResult()).add(task);
    }

    private Des2InstanceContext createDes2InstanceContext(Map<String, ObjectNode> classNodes,
                                                          ContextProcessor<Des2InstanceContext> des2InstanceContextProcessor){

        SimpleContextIds simpleContextIds = new SimpleContextIds(
                TASK_COMMON,
                WRAPPER_SPECIFIC,
                WRAPPER_COLLECTION,
                WRAPPER_CUSTOM,
                WRAPPER_MAP
        );

        HashMap<String, Class<?>> map = new HashMap<>();
        map.put("Des2InstanceTC0", Des2InstanceTC0.class);
        map.put("Des2InstanceInnerTC0", Des2InstanceInnerTC0.class);

        return new SkeletonDes2InstanceContext(
                simpleContextIds,
                classNodes,
                new SkeletonClassNameExtractor(),
                new AnnotationExtractor(),
                classMembersPartHandler,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classPartCollectorPath,
                new StrType2CollectionOptConverter(classMembersPartHandler),
                new StrType2MapOptConverter(classMembersPartHandler),
                new ClassName2Instance(map),
                new ToInstanceOC(map, classHeaderPartHandler, classPartCollectorPath),
                des2InstanceContextProcessor
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
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(
                String.class,
                Integer.class,
                Float.class,
                Double.class,
                Character.class,
                Boolean.class,
                Des2InstanceInnerTC0.class
        ));

        return new CollectionTypeChecker(types, argumentTypes);
    }

    private SimpleChecker<Field> createMapTypeChecker(){
        Set<Class<?>> mTypes = new HashSet<>(Collections.singletonList(Map.class));
        Set<Class<?>> keyArgTypes = new HashSet<>(Arrays.asList(Integer.class, String.class));
        Set<Class<?>> valueArgTypes = new HashSet<>(Arrays.asList(Integer.class, String.class, Des2InstanceInnerTC0.class));

        return new MapTypeChecker(mTypes, keyArgTypes, valueArgTypes);
    }

    private ContextProcessor<ClassContext> createClassContextProcessor(){

        SimpleChecker<Field> collectionTypeChecker = createCollectionTypeChecker();
        SimpleChecker<Field> mapTypeChecker = createMapTypeChecker();
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

        ContextTask<ClassContext> task = new ContextTask<>(TASK_COMMON, new SkeletonAggregateResult());
        task.add(new ClassSignatureTaskHandler(WRAPPER_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandler(WRAPPER_SPECIFIC, specAllowedClassChecker))
                .add(new ClassCustomTaskHandler(WRAPPER_CUSTOM, new AllowedStringChecker("Des2InstanceInnerTC0")))
                .add(new ClassContainerTaskHandler(WRAPPER_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandler(WRAPPER_MAP, mapTypeChecker));

        return new ContextProcessor<ClassContext>(new SkeletonAggregateResult()).add(task);
    }

    private ContextProcessor<InstanceContext> createInstanceProcessor(){

        ContextTask<InstanceContext> task = new ContextTask<>(TASK_COMMON, new SkeletonAggregateResult());
        task.add(new InstanceHeaderTaskHandler(WRAPPER_HEADER))
                .add(new InstanceSpecificTaskHandler(WRAPPER_SPECIFIC))
                .add(new InstanceCustomTaskHandler(WRAPPER_CUSTOM))
                .add(new InstanceCollectionTaskHandler(WRAPPER_COLLECTION))
                .add(new InstanceMapTaskHandler(WRAPPER_MAP));

        return new ContextProcessor<InstanceContext>(new SkeletonAggregateResult()).add(task);
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
