// TODO: 08.11.2020 del 

//package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance;
//
//import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
//import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
//import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
//import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SKInstanceMembersPartHandler;
//import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
//import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
//import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InnerInstanceProcessorTC0;
//import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InstanceProcessorTC0;
//import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContextOld;
//import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
//import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
//import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
//import org.KasymbekovPN.Skeleton.util.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@DisplayName("InstanceProcessor. Testing of:")
//public class InstanceProcessorTest {
//
//    @Test
//    void test() throws Exception {
//
//        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
//                .setTypes(Set.class, List.class)
//                .setArgumentTypes(String.class, Integer.class, Float.class, InnerInstanceProcessorTC0.class)
//                .build();
//
//        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
//                .setTypes(Map.class)
//                .setKeyArgumentsTypes(Integer.class)
//                .setValueArgumentsTypes(Integer.class, InnerInstanceProcessorTC0.class)
//                .build();
//
//        ClassContext classContext = UClassSerialization.createClassContext(
//                new SKCollector(),
//                new SKContextStateCareTaker<>()
//        );
//
//        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
//                new SKSimpleChecker<Class<?>>(int.class, float.class),
//                new SKSimpleChecker<String>("InnerInstanceProcessorTC0"),
//                new AnnotationExtractor(),
//                collectionTypeChecker,
//                mapTypeChecker
//        );
//
//        HashMap<String, ObjectNode> classNodes = new HashMap<>();
//
//        classContext.getContextStateCareTaker().push(
//                new SKClassContextStateMemento(
//                        InstanceProcessorTC0.class,
//                        new AnnotationExtractor()
//                )
//        );
//        classProcessor.handle(classContext);
//        classNodes.put("InstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());
//
//        classContext.getContextStateCareTaker().push(
//                new SKClassContextStateMemento(
//                        InnerInstanceProcessorTC0.class,
//                        new AnnotationExtractor()
//                )
//        );
//        classProcessor.handle(classContext);
//        classNodes.put("InnerInstanceProcessorTC0", (ObjectNode) classContext.getCollector().detachNode());
//
//        InstanceProcessorTC0 instance = new InstanceProcessorTC0();
//
//        OldContextProcessor<InstanceContextOld> instanceProcessor = UInstanceSerializationOld.createInstanceProcessor();
//
//        InstanceContextOld instanceContextOld = UInstanceSerializationOld.createInstanceContext(
//                classNodes,
//                instanceProcessor,
//                instance,
//                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
//                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
//                USKClassHeaderPartHandler.DEFAULT,
//                USKClassMembersPartHandler.DEFAULT,
//                new SKInstanceMembersPartHandler()
//        );
//
//        instanceProcessor.handle(instanceContextOld);
//
//        //<
//        System.out.println(instanceContextOld.getCollector().getNode());
//        //<
//    }
//}
