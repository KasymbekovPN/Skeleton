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
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Des2InstanceCollectionTaskHandler. Testing of:")
public class Des2InstanceCollectionTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NUMBER_OF_MEMBERS_ARE_ZERO = "Number of members are zero";

    @DisplayName("check method - check valid")
    @Test
    void testCheckMethodNotValid() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper("collection");

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
        TestedClassWrapper tested = new TestedClassWrapper("collection");

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
                .setTypes(
                        Set.class,
                        List.class
                )
                .setArgumentTypes(
                        Integer.class,
                        Boolean.class,
                        Character.class,
                        String.class
                )
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes()
                .setKeyArgumentsTypes()
                .setValueArgumentsTypes()
                .build();

        ClassContext classContext = UClassSerialization.createClassContext(
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                null,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );

        OldContextProcessor<ClassContext> classProcessor = UClassSerialization.createClassProcessor(
                USKClassHeaderPartHandler.DEFAULT,
                new SKSimpleChecker<Class<?>>(),
                new SKSimpleChecker<String>(),
                collectionTypeChecker,
                mapTypeChecker
        );

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.attachClass(TestClass.class);
        classProcessor.handle(classContext);

        classNodes.put("TestClass", (ObjectNode)classContext.getCollector().detachNode());

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
        original.setIntSet(new HashSet<>(){{
            add(1);
            add(2);
        }});
        original.setIntList(new ArrayList<>(){{
            add(10);
            add(20);
        }});
        original.setBooleanSet(new HashSet<>(){{
            add(false);
            add(true);
        }});
        original.setBooleanList(new ArrayList<>(){{
            add(true);
            add(false);
            add(true);
        }});
        original.setCharacterSet(new HashSet<>(){{
            add('a');
            add('b');
        }});
        original.setCharacterList(new ArrayList<>(){{
            add('0');
            add('1');
        }});
        original.setStringSet(new HashSet<>(){{
            add("hello");
            add("world");
        }});
        original.setStringList(new ArrayList<>(){{
            add("abc");
            add("xyz");
        }});

        instanceContext.attachInstance(original);
        instanceProcessor.handle(instanceContext);

        ObjectNode serData = (ObjectNode) instanceContext.getCollector().detachNode();

        TestedClassWrapper tested = new TestedClassWrapper("collection");

        HashMap<String, Class<?>> map = new HashMap<>();
        Des2InstanceCxt context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                classNodes,
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
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

    private static class TestedClassWrapper extends Des2InstanceCollectionTaskHandler{
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
        protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements Des2InstanceContextStateMemento{
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
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private Set<Integer> intSet;

        @SkeletonMember
        private List<Integer> intList;

        @SkeletonMember
        private Set<Boolean> booleanSet;

        @SkeletonMember
        private List<Boolean> booleanList;

        @SkeletonMember
        private Set<Character> characterSet;

        @SkeletonMember
        private List<Character> characterList;

        @SkeletonMember
        private Set<String> stringSet;

        @SkeletonMember
        private List<String> stringList;

        public void setIntSet(Set<Integer> intSet) {
            this.intSet = intSet;
        }

        public void setIntList(List<Integer> intList) {
            this.intList = intList;
        }

        public void setBooleanSet(Set<Boolean> booleanSet) {
            this.booleanSet = booleanSet;
        }

        public void setBooleanList(List<Boolean> booleanList) {
            this.booleanList = booleanList;
        }

        public void setCharacterSet(Set<Character> characterSet) {
            this.characterSet = characterSet;
        }

        public void setCharacterList(List<Character> characterList) {
            this.characterList = characterList;
        }

        public void setStringSet(Set<String> stringSet) {
            this.stringSet = stringSet;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return Objects.equals(intSet, testClass.intSet) &&
                    Objects.equals(intList, testClass.intList) &&
                    Objects.equals(booleanSet, testClass.booleanSet) &&
                    Objects.equals(booleanList, testClass.booleanList) &&
                    Objects.equals(characterSet, testClass.characterSet) &&
                    Objects.equals(characterList, testClass.characterList) &&
                    Objects.equals(stringSet, testClass.stringSet) &&
                    Objects.equals(stringList, testClass.stringList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intSet, intList, booleanSet, booleanList, characterSet, characterList, stringSet, stringList);
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "intSet=" + intSet +
                    ", intList=" + intList +
                    ", booleanSet=" + booleanSet +
                    ", booleanList=" + booleanList +
                    ", characterSet=" + characterSet +
                    ", characterList=" + characterList +
                    ", stringSet=" + stringSet +
                    ", stringList=" + stringList +
                    '}';
        }
    }
}