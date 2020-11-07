package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@DisplayName("InstanceSpecificTaskHandler. Testing of:")
public class InstanceSpecificTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String IS_EMPTY = "Values by '%s' is empty";

    private static Object[][] getDoItMethodTestData(){
        return new Object[][]{
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello"),
                        true
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'z'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, true),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 123),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
                {
                        new MutablePair<Integer, Integer>(123, 1234),
                        new MutablePair<Boolean, Boolean>(true, false),
                        new MutablePair<Character, Character>('z', 'a'),
                        new MutablePair<String, String>("hello", "hello1"),
                        false
                },
        };
    }

    @DisplayName("check method - valid")
    @Test
    void testCheckMethod_valid() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.SPECIFIC);
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        instanceContext.getContextStateCareTaker().push(new NotValidMemento());
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isFalse();
        Assertions.assertThat(tested.getResult().getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("check method - empty")
    @Test
    void testCheckMethod_empty() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.SPECIFIC);
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        instanceContext.getContextStateCareTaker().push(new EmptyMemento());
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isFalse();
        Assertions.assertThat(tested.getResult().getStatus()).isEqualTo(String.format(IS_EMPTY, UHandlerIds.SPECIFIC));
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getDoItMethodTestData")
    void testDoItMethod(Pair<Integer, Integer> intPair,
                        Pair<Boolean, Boolean> booleanPair,
                        Pair<Character, Character> characterPair,
                        Pair<String, String> stringPair,
                        boolean result) throws Exception {
        Tested tested = new Tested(UHandlerIds.SPECIFIC);
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("TestClass", getClassSerializedData(TestClass.class));
        }};
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );

        TestClass instance = new TestClass(intPair.getLeft(), booleanPair.getLeft(), characterPair.getLeft(), stringPair.getLeft());
        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                instance,
                classNodes.get("TestClass"),
                instanceContext
        ));
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isTrue();
        Assertions.assertThat(tested.getResult().getStatus()).isEmpty();

        tested.doIt(instanceContext);
        Node nodeFromContext = instanceContext.getCollector().detachNode();

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        collector.addProperty("intValue", intPair.getRight());
        collector.addProperty("booleanValue", booleanPair.getRight());
        collector.addProperty("charValue", characterPair.getRight());
        collector.addProperty("stringObject", stringPair.getRight());
        collector.reset();
        Node checkNode = collector.detachNode();
        Assertions.assertThat(nodeFromContext.equals(checkNode)).isEqualTo(result);
    }

    private ObjectNode getClassSerializedData(Class<?> clazz) throws Exception {
        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes()
                .setArgumentTypes()
                .build();
        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes()
                .setKeyArgumentsTypes()
                .setValueArgumentsTypes()
                .build();
        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(int.class, boolean.class, char.class, String.class),
                new SKSimpleChecker<String>(),
                new AnnotationExtractor(),
                collectionTypeChecker,
                mapTypeChecker
        );
        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );
        classContext.getContextStateCareTaker().push(new SKClassContextStateMemento(
                TestClass.class,
                new AnnotationExtractor()
        ));
        classProcessor.handle(classContext);

        return (ObjectNode) classContext.getCollector().detachNode();
    }

    private static class Tested extends InstanceSpecificTaskHandler {

        public Tested(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        public Tested(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }

        @Override
        public void check(InstanceContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements InstanceContextStateMemento {
        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Number getClassModifiers() {
            return null;
        }

        @Override
        public Map<String, Object> getFieldValues(String kind) {
            return null;
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        }

        @Override
        public SimpleResult getValidationResult() {
            SKSimpleResult result = new SKSimpleResult();
            result.setFailStatus(NOT_VALID);
            return result;
        }
    }

    private static class EmptyMemento implements InstanceContextStateMemento{

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Number getClassModifiers() {
            return null;
        }

        @Override
        public Map<String, Object> getFieldValues(String kind) {
            return new HashMap<>();
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private int intValue;

        @SkeletonMember
        private boolean booleanValue;

        @SkeletonMember
        private char charValue;

        @SkeletonMember
        private String stringObject;

        public TestClass(int intValue, boolean booleanValue, char charValue, String stringObject) {
            this.intValue = intValue;
            this.booleanValue = booleanValue;
            this.charValue = charValue;
            this.stringObject = stringObject;
        }
    }
}
