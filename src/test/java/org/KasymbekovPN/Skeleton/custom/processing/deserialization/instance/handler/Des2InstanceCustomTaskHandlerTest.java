package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SKInstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.SKDes2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.OldClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
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

@DisplayName("Des2InstanceCustomTaskHandler. Testing of")
public class Des2InstanceCustomTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NUMBER_OF_MEMBERS_ARE_ZERO = "Number of members are zero";

    @DisplayName("check method - check valid")
    @Test
    void testCheckMethodNotValid() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper("custom");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
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
    void testCheckMethodGetMembers() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper("custom");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                new HashMap<>(),
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
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

        OldClassContext oldClassContext = UClassSerializationOld.createClassContext(
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                null,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );

        OldContextProcessor<OldClassContext> classProcessor = UClassSerializationOld.createClassProcessor(
                USKClassHeaderPartHandler.DEFAULT,
                new SKSimpleChecker<Class<?>>(),
                new SKSimpleChecker<String>("InnerTestClass"),
                collectionTypeChecker,
                mapTypeChecker
        );

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        oldClassContext.attachClass(TestClass.class);
        classProcessor.handle(oldClassContext);
        classNodes.put("TestClass", (ObjectNode) oldClassContext.getCollector().detachNode());

        oldClassContext.attachClass(InnerTestClass.class);
        classProcessor.handle(oldClassContext);
        classNodes.put("InnerTestClass", (ObjectNode) oldClassContext.getCollector().detachNode());

        OldContextProcessor<InstanceContext> instanceProcessor = UInstanceSerialization.createInstanceProcessor();
        InstanceContext instanceContext = UInstanceSerialization.createInstanceContext(
                classNodes,
                instanceProcessor,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                new SKInstanceMembersPartHandler()
        );

        TestClass original = new TestClass();
        original.setInner(new InnerTestClass());

        instanceContext.attachInstance(original);
        instanceProcessor.handle(instanceContext);

        ObjectNode serData = (ObjectNode) instanceContext.getCollector().detachNode();

        TestedClassWrapper tested = new TestedClassWrapper("custom");

        HashMap<String, Class<?>> map = new HashMap<>(){{
            put("InnerTestClass", InnerTestClass.class);
        }};
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                classNodes,
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
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
        assertThat(restored).isEqualTo(original);
    }

    private static class TestedClassWrapper extends Des2InstanceCustomTaskHandler{
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

    @SkeletonClass(name = "InnerTestClass")
    public static class InnerTestClass{

        private int intValue = 123;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerTestClass that = (InnerTestClass) o;
            return intValue == that.intValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue);
        }

        @Override
        public String toString() {
            return "InnerTestClass{" +
                    "intValue=" + intValue +
                    '}';
        }
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember(name = "InnerTestClass")
        private InnerTestClass inner;

        public void setInner(InnerTestClass inner) {
            this.inner = inner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return Objects.equals(inner, testClass.inner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(inner);
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "inner=" + inner +
                    '}';
        }
    }
}
