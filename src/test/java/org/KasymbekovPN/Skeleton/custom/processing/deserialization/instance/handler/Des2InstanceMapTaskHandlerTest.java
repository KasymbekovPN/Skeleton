package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.functional.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.checker.MapTypeChecker;
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
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;
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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Des2InstanceMapTaskHandler. Testing of:")
public class Des2InstanceMapTaskHandlerTest {
    private static final String NOT_VALID = "not valid";
    private static final String NUMBER_OF_MEMBERS_ARE_ZERO = "Number of members are zero";

    @DisplayName("check method - check valid")
    @Test
    void testCheckMethodNotValid() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty, InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
        TestedClassWrapper tested = new TestedClassWrapper("map");

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
        TestedClassWrapper tested = new TestedClassWrapper("map");

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
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class, String.class)
                .setValueArgumentsTypes(Integer.class, String.class, Double.class)
                .build();

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(),
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
        original.setDoubleByStr(new HashMap<>(){{
            put("double_0", 123.0);
            put("double_1", 456.0);
        }});
        original.setIntByInt(new HashMap<>(){{
            put(1, 1);
            put(2, 8);
            put(3, 27);
        }});
        original.setStrByInt(new HashMap<>(){{
            put(10, "ten");
            put(11, "eleven");
        }});

        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                original,
                classNodes.get("TestClass"),
                instanceContext
        ));
        instanceProcessor.handle(instanceContext);

        ObjectNode serData = (ObjectNode) instanceContext.getCollector().detachNode();

        TestedClassWrapper tested = new TestedClassWrapper("map");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                classNodes,
                USKDes2Instance.createDummyObjectInstanceGenerator(),
                USKDes2Instance.createProcessor(),
                new SKContextStateCareTaker<>()
        );

        TestClass restored = new TestClass();
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                restored,
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
        assertThat(context.getContextStateCareTaker().peek().getInstance()).isEqualTo(original);
    }

    private static class TestedClassWrapper extends Des2InstanceMapTaskHandler{
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
        private Map<Integer, Integer> intByInt;

        @SkeletonMember
        private Map<Integer, String> strByInt;

        @SkeletonMember
        private Map<String, Double> doubleByStr;

        public void setIntByInt(Map<Integer, Integer> intByInt) {
            this.intByInt = intByInt;
        }

        public void setStrByInt(Map<Integer, String> strByInt) {
            this.strByInt = strByInt;
        }

        public void setDoubleByStr(Map<String, Double> doubleByStr) {
            this.doubleByStr = doubleByStr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return Objects.equals(intByInt, testClass.intByInt) &&
                    Objects.equals(strByInt, testClass.strByInt) &&
                    Objects.equals(doubleByStr, testClass.doubleByStr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intByInt, strByInt, doubleByStr);
        }
    }
}
