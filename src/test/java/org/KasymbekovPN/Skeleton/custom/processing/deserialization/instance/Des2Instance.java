package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance;

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
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2CollectionOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2MapOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes.Des2InstanceInnerTC0;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes.Des2InstanceTC0;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.OldDes2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.SKOldDes2InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCollectionTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceMapTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Des2Instance {

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

        OldContextProcessor<ClassContext> classOldContextProcessor = createClassContextProcessor();

        ClassContext classContext = createClassContext();
        classContext.attachClass(Des2InstanceTC0.class);
        classOldContextProcessor.handle(classContext);

//        classNodes.put("Des2InstanceTC0", (ObjectNode) classContext.getCollector().attachNode(new ObjectNode(null)));
        //<
        ObjectNode objectNode1 = new ObjectNode(null);
        classNodes.put("Des2InstanceTC0", (ObjectNode) classContext.getCollector().attach(objectNode1, objectNode1).getLeft());

        classContext.attachClass(Des2InstanceInnerTC0.class);
        classOldContextProcessor.handle(classContext);
        classNodes.put("Des2InstanceInnerTC0", (ObjectNode) classContext.getCollector().getNode());

        //<
        System.out.println(classNodes);

        OldContextProcessor<InstanceContext> instanceProcessor = createInstanceProcessor();
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

        OldContextProcessor<OldDes2InstanceContext> des2InstanceOldContextProcessor = createDes2InstanceContextProcessor();
        OldDes2InstanceContext oldDes2InstanceContext = createDes2InstanceContext(
                classNodes,
                des2InstanceOldContextProcessor
        );

        Des2InstanceTC0 restoredInstance = new Des2InstanceTC0();
        //<
        System.out.println("before : " + restoredInstance);
        //<
//        des2InstanceContext.attachInstance(restoredInstance);
        //<
        oldDes2InstanceContext.push(restoredInstance, serializedData);
        des2InstanceOldContextProcessor.handle(oldDes2InstanceContext);

        //<
        System.out.println("after : " + restoredInstance);
        //<
    }

    private OldContextProcessor<OldDes2InstanceContext> createDes2InstanceContextProcessor(){

        OLdContextTask<OldDes2InstanceContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new Des2InstanceSpecificTaskHandlerOld(WRAPPER_SPECIFIC))
                .add(new Des2InstanceCollectionTaskHandlerOld(WRAPPER_COLLECTION))
                .add(new Des2InstanceCustomTaskHandlerOld(WRAPPER_CUSTOM))
                .add(new Des2InstanceMapTaskHandlerOld(WRAPPER_MAP));

        return new OldContextProcessor<OldDes2InstanceContext>().add(task);
    }

    private OldDes2InstanceContext createDes2InstanceContext(Map<String, ObjectNode> classNodes,
                                                             OldContextProcessor<OldDes2InstanceContext> des2InstanceOldContextProcessor){

        SKSimpleContextIds SKSimpleContextIds = new SKSimpleContextIds(
                TASK_COMMON,
                WRAPPER_SPECIFIC,
                WRAPPER_COLLECTION,
                WRAPPER_CUSTOM,
                WRAPPER_MAP
        );

        HashMap<String, Class<?>> map = new HashMap<>();
        map.put("Des2InstanceTC0", Des2InstanceTC0.class);
        map.put("Des2InstanceInnerTC0", Des2InstanceInnerTC0.class);

        return new SKOldDes2InstanceContext(
                SKSimpleContextIds,
                classNodes,
                new ClassNameExtractor(),
                new AnnotationExtractor(),
                classMembersPartHandler,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classPartCollectorPath,
                new StrType2CollectionOptConverter(classMembersPartHandler),
                new StrType2MapOptConverter(classMembersPartHandler),
                new ClassName2Instance(map),
                new ToInstanceOC(map, classHeaderPartHandler, classPartCollectorPath),
                des2InstanceOldContextProcessor
        );
    }

    private ClassContext createClassContext(){
        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                WRAPPER_SIGNATURE,
                WRAPPER_SPECIFIC,
                WRAPPER_CUSTOM,
                WRAPPER_COLLECTION,
                WRAPPER_MAP
        );

        return new SKClassContext(
                contextIds,
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                null,
                new SKCollector(),
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

    private OldContextProcessor<ClassContext> createClassContextProcessor(){

        SimpleChecker<Field> collectionTypeChecker = createCollectionTypeChecker();
        SimpleChecker<Field> mapTypeChecker = createMapTypeChecker();
        SimpleChecker<Class<?>> specAllowedClassChecker = new SKSimpleChecker<>(
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

        OLdContextTask<ClassContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new ClassSignatureTaskHandlerOld(WRAPPER_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandlerOld(WRAPPER_SPECIFIC, specAllowedClassChecker))
                .add(new ClassCustomTaskHandlerOld(WRAPPER_CUSTOM, new SKSimpleChecker<>("Des2InstanceInnerTC0")))
                .add(new ClassContainerTaskHandlerOld(WRAPPER_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandlerOld(WRAPPER_MAP, mapTypeChecker));

        return new OldContextProcessor<ClassContext>().add(task);
    }

    private OldContextProcessor<InstanceContext> createInstanceProcessor(){

        OLdContextTask<InstanceContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new InstanceHeaderTaskHandlerOld(WRAPPER_HEADER))
                .add(new InstanceSpecificTaskHandlerOld(WRAPPER_SPECIFIC))
                .add(new InstanceCustomTaskHandlerOld(WRAPPER_CUSTOM))
                .add(new InstanceCollectionTaskHandlerOld(WRAPPER_COLLECTION))
                .add(new InstanceMapTaskHandlerOld(WRAPPER_MAP));

        return new OldContextProcessor<InstanceContext>().add(task);
    }

    private InstanceContext createInstanceContext(Map<String, ObjectNode> classNodes,
                                                  OldContextProcessor<InstanceContext> processor){
        InstanceMembersPartHandler instanceMembersPartHandler = new SKInstanceMembersPartHandler();

        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                WRAPPER_HEADER, WRAPPER_SPECIFIC, WRAPPER_CUSTOM, WRAPPER_COLLECTION, WRAPPER_MAP
        );

        return new SKInstanceContext(
                contextIds,
                classNodes,
                new SKCollector(),
                processor,
                null,
                classPartCollectorPath,
                membersPartCollectorPath,
                classHeaderPartHandler,
                classMembersPartHandler,
                instanceMembersPartHandler,
                new ClassNameExtractor(),
                new InstanceDataMembersExtractor()
        );
    }
}
