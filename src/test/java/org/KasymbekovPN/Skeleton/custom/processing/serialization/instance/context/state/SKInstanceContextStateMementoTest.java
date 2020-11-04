package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

@DisplayName("SKInstanceContextStateMemento. Testing of:")
public class SKInstanceContextStateMementoTest {

    private static final String INSTANCE_IS_NULL = "The instance is null";
    private static final String CLASS_NODE_IS_NULL = "The class node is null";
    private static final String INSTANCE_CONTEXT_IS_NULL = "The instance context is null";
    private static final String CLASS_NODE_DOES_NOT_CONTAIN_CLASS_PART = "The class node doesn't contain class part";
    private static final String CLASS_PART_IS_NOT_COMPLETELY = "The class part doesn't contain name or/and modifiers";
    private static final String CLASS_NODE_DOES_NOT_CONTAIN_MEMBERS_PART = "The class node doesn't contain members part";

    private static Object[][] getValidateMethodTestData(){

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );

        SKCollector collector = new SKCollector();

        addEmptyClassPart(collector);
        ObjectNode classNodeWithEmptyClassPart = (ObjectNode) collector.detachNode();

        addClassPartWithoutName(collector);
        ObjectNode classNodeClassPartWithoutName = (ObjectNode) collector.detachNode();

        addClassPartWithoutModifiers(collector);
        ObjectNode classNodeClassPartWithoutModifiers = (ObjectNode) collector.detachNode();

        addClassPart(collector);
        ObjectNode classNodeWithoutMembersPart = (ObjectNode) collector.detachNode();

        return new Object[][]{
                {null, null, null, false, INSTANCE_IS_NULL},
                {new Object(), null, null, false, CLASS_NODE_IS_NULL},
                {new Object(), new ObjectNode(null), null, false, INSTANCE_CONTEXT_IS_NULL},
                {new Object(), new ObjectNode(null), instanceContext, false, CLASS_NODE_DOES_NOT_CONTAIN_CLASS_PART},
                {new Object(), classNodeWithEmptyClassPart, instanceContext, false, CLASS_PART_IS_NOT_COMPLETELY},
                {new Object(), classNodeClassPartWithoutName, instanceContext, false, CLASS_PART_IS_NOT_COMPLETELY},
                {new Object(), classNodeClassPartWithoutModifiers, instanceContext, false, CLASS_PART_IS_NOT_COMPLETELY},
                {new Object(), classNodeWithoutMembersPart, instanceContext, false, CLASS_NODE_DOES_NOT_CONTAIN_MEMBERS_PART}
        };
    }

    private static void addEmptyClassPart(Collector collector){
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
    }

    private static void addClassPartWithoutName(Collector collector){
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "someName");
    }

    private static void addClassPartWithoutModifiers(Collector collector){
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("modifiers", 2);
    }

    private static void addClassPart(Collector collector){
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("modifiers", 2);
        collector.addProperty("name", "someName");
    }

    @DisplayName("validate method test")
    @ParameterizedTest
    @MethodSource("getValidateMethodTestData")
    void testValidateMethod(Object instance,
                            ObjectNode classNode,
                            InstanceContext instanceContext,
                            boolean success,
                            String status) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        SKInstanceContextStateMemento mem = new SKInstanceContextStateMemento(instance, classNode, instanceContext);
        mem.validate();

        SimpleResult result = mem.getValidationResult();
        Assertions.assertThat(result.isSuccess()).isEqualTo(success);
        Assertions.assertThat(result.getStatus()).isEqualTo(status);
    }
    
    @DisplayName("getClassName method")
    @Test
    void testGetClassName() throws Exception {

        ObjectNode serializeClassData = serializeClass(TestClass.class);

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        SKInstanceContextStateMemento mem = new SKInstanceContextStateMemento(new TestClass(), serializeClassData, instanceContext);
        mem.validate();

        SimpleResult validationResult = mem.getValidationResult();
        Assertions.assertThat(validationResult.isSuccess()).isTrue();
        Assertions.assertThat(validationResult.getStatus()).isEmpty();

        Assertions.assertThat(mem.getClassName()).isEqualTo("TestClass");
    }

    // todo: test method getClassModifiers
    // todo: test method getFieldValues

    private ObjectNode serializeClass(Class<?> clazz) throws Exception {

        ClassContext context = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes()
                .setArgumentTypes()
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes()
                .setKeyArgumentsTypes()
                .setValueArgumentsTypes()
                .build();

        Processor<ClassContext> processor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(int.class),
                new SKSimpleChecker<String>(),
                new AnnotationExtractor(),
                collectionTypeChecker,
                mapTypeChecker
        );

        context.getContextStateCareTaker().push(
                new SKClassContextStateMemento(clazz, new AnnotationExtractor())
        );
        processor.handle(context);

        return (ObjectNode) context.getCollector().detachNode();
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private int intValue = 123;
    }
}
