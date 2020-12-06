package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.SKDes2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.*;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Des2InstanceSpecificTaskHandler. Testing of :")
public class Des2InstanceSpecificTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NUMBER_OF_MEMBERS_ARE_ZERO = "Number of members are zero";

    @DisplayName("check method - check valid")
    @Test
    void testCheckMethodNotValid() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty, InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
        TestedClassWrapper tested = new TestedClassWrapper("specific");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                USKDes2Instance.createDummyObjectInstanceGenerator(),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        context.getContextStateCareTaker().push(new NotValidMemento());

        tested.check(context);
        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("doIt method - get members")
    @Test
    void testCheckMethodGetMembers() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty, InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
        TestedClassWrapper tested = new TestedClassWrapper("specific");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                USKDes2Instance.createDummyObjectInstanceGenerator(),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        context.getContextStateCareTaker().push(new ZeroMembersMemento());

        tested.check(context);
        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NUMBER_OF_MEMBERS_ARE_ZERO);
    }

    @DisplayName("doIt method")
    @Test
    void testDoItMethod() throws Exception {

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes()
                .setArgumentTypes()
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes()
                .setKeyArgumentsTypes()
                .setValueArgumentsTypes()
                .build();

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(
                        int.class,
                        Integer.class,
                        boolean.class,
                        Boolean.class,
                        char.class,
                        Character.class,
                        String.class
                ),
                new SKSimpleChecker<String>(),
                new AnnotationExtractor(),
                collectionTypeChecker,
                mapTypeChecker
        );

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        TestClass.class,
                        new AnnotationExtractor()
                )
        );
        classProcessor.handle(classContext);

        classNodes.put("TestClass", (ObjectNode) classContext.getCollector().detachNode());

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );
        ContextProcessor<InstanceContext> instanceProcessor = UInstanceSerialization.createProcessor();

        TestClass original = new TestClass();
        original.setIntValue(100);
        original.setIntObject(123);
        original.setBooleanValue(true);
        original.setBooleanObject(false);
        original.setCharValue('a');
        original.setCharacterObject('b');
        original.setStringObject("hello");
        original.setWithoutAnnotation(333);

        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                original,
                classNodes.get("TestClass"),
                instanceContext
        ));
        instanceProcessor.handle(instanceContext);

        ObjectNode serData = (ObjectNode) instanceContext.getCollector().detachNode();

        TestedClassWrapper tested = new TestedClassWrapper("specific");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                classNodes,
                USKDes2Instance.createDummyObjectInstanceGenerator(),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new TestClass(),
                serData,
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );
        context.getContextStateCareTaker().push(mem);

        tested.check(context);
        assertThat(tested.getResult().isSuccess()).isTrue();

        tested.doIt(context);
        TestClass restored = (TestClass) context.getContextStateCareTaker().peek().getInstance();
        assertThat(restored.specialEquals(original)).isTrue();
        assertThat(restored).isNotEqualTo(original);
    }

    private static class TestedClassWrapper extends Des2InstanceSpecificTaskHandler{
        public TestedClassWrapper(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        public TestedClassWrapper(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }

        @Override
        protected void check(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements Des2InstanceContextStateMemento {
        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public Set<Triple<Field, Node, ObjectNode>> getMembersData(String kind) {
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

        @Override
        public Des2InstanceContextStateMemento createNew(Object instance, ObjectNode serData) {
            return null;
        }
    }

    private static class ZeroMembersMemento implements Des2InstanceContextStateMemento{
        @Override
        public Object getInstance() {
            return null;
        }

        @Override
        public Set<Triple<Field, Node, ObjectNode>> getMembersData(String kind) {
            return new HashSet<>();
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }

        @Override
        public Des2InstanceContextStateMemento createNew(Object instance, ObjectNode serData) {
            return null;
        }
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private int intValue;

        @SkeletonMember
        private Integer intObject;

        @SkeletonMember
        private boolean booleanValue;

        @SkeletonMember
        private Boolean booleanObject;

        @SkeletonMember
        private char charValue;

        @SkeletonMember
        private Character characterObject;

        @SkeletonMember
        private String stringObject;

        private int withoutAnnotation;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public void setIntObject(Integer intObject) {
            this.intObject = intObject;
        }

        public void setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public void setBooleanObject(Boolean booleanObject) {
            this.booleanObject = booleanObject;
        }

        public void setCharValue(char charValue) {
            this.charValue = charValue;
        }

        public void setCharacterObject(Character characterObject) {
            this.characterObject = characterObject;
        }

        public void setStringObject(String stringObject) {
            this.stringObject = stringObject;
        }

        public void setWithoutAnnotation(int withoutAnnotation) {
            this.withoutAnnotation = withoutAnnotation;
        }

        public boolean specialEquals(TestClass o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return intValue == o.intValue &&
                    booleanValue == o.booleanValue &&
                    charValue == o.charValue &&
                    Objects.equals(intObject, o.intObject) &&
                    Objects.equals(booleanObject, o.booleanObject) &&
                    Objects.equals(characterObject, o.characterObject) &&
                    Objects.equals(stringObject, o.stringObject);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return intValue == testClass.intValue &&
                    booleanValue == testClass.booleanValue &&
                    charValue == testClass.charValue &&
                    withoutAnnotation == testClass.withoutAnnotation &&
                    Objects.equals(intObject, testClass.intObject) &&
                    Objects.equals(booleanObject, testClass.booleanObject) &&
                    Objects.equals(characterObject, testClass.characterObject) &&
                    Objects.equals(stringObject, testClass.stringObject);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue, intObject, booleanValue, booleanObject, charValue, characterObject, stringObject, withoutAnnotation);
        }


        @Override
        public String toString() {
            return "TestClass{" +
                    "intValue=" + intValue +
                    ", intObject=" + intObject +
                    ", booleanValue=" + booleanValue +
                    ", booleanObject=" + booleanObject +
                    ", charValue=" + charValue +
                    ", characterObject=" + characterObject +
                    ", stringObject='" + stringObject + '\'' +
                    ", withoutAnnotation=" + withoutAnnotation +
                    '}';
        }
    }
}
