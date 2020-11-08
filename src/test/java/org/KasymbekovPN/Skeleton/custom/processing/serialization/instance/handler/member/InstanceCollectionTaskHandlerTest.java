package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@DisplayName("InstanceCollectionTaskHandler. Testing of:")
public class InstanceCollectionTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String VALUES_IS_EMPTY = "Values by '%s' are empty";

    private static Object[][] getDoItMethodTestData(){
        return new Object[][]{
                {
                        new MutablePair<Set<Integer>, Set<Integer>>(
                                new HashSet<>(Arrays.asList(1,2,3)),
                                new HashSet<>(Arrays.asList(1,2,3))
                        ),
                        new MutablePair<List<Float>, List<Float>>(
                                new ArrayList<>(Arrays.asList(1.1f, 1.2f)),
                                new ArrayList<>(Arrays.asList(1.1f, 1.2f))
                        ),
                        true
                },
                {
                        new MutablePair<Set<Integer>, Set<Integer>>(
                                new HashSet<>(Arrays.asList(1,2,3)),
                                new HashSet<>(Arrays.asList(2,3))
                        ),
                        new MutablePair<List<Float>, List<Float>>(
                                new ArrayList<>(Arrays.asList(1.1f, 1.2f)),
                                new ArrayList<>(Arrays.asList(1.1f, 1.2f, 0.1f))
                        ),
                        false
                },
        };
    }

    @DisplayName("check method - valid")
    @Test
    void testCheckMethod_valid() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.COLLECTION);
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

    @DisplayName("check method - empty values")
    @Test
    void testCheckMethod_emptyValues() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.COLLECTION);
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        instanceContext.getContextStateCareTaker().push(new EmptyValuesMemento());
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isFalse();
        Assertions.assertThat(tested.getResult().getStatus()).isEqualTo(String.format(VALUES_IS_EMPTY, UHandlerIds.COLLECTION));
    }

    @DisplayName("doIt method - primitive")
    @ParameterizedTest
    @MethodSource("getDoItMethodTestData")
    void testDoItMethod_primitive(Pair<Set<Integer>, Set<Integer>> intSet,
                                  Pair<List<Float>, List<Float>> floatList,
                                  boolean result) throws Exception {
        Tested tested = new Tested(UHandlerIds.COLLECTION);
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("TestClass1", getClassSerializedData(TestClass1.class));
        }};

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );

        TestClass1 instance = new TestClass1(intSet.getLeft(), floatList.getLeft());
        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                instance,
                classNodes.get("TestClass1"),
                instanceContext
        ));
        tested.handle(instanceContext);

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());

        collector.beginArray("intSet");
        for (Integer integer : intSet.getRight()) {
            collector.addProperty(integer);
        }
        collector.end();

        collector.beginArray("floatList");
        for (Float aFloat : floatList.getRight()) {
            collector.addProperty(aFloat);
        }
        collector.end();
        collector.reset();

        Node node = instanceContext.getCollector().getNode();
        Node checkNode = collector.getNode();
        Assertions.assertThat(node.equals(checkNode)).isEqualTo(result);
    }


    @DisplayName("doIt method - custom")
    @Test
    void testDoItMethod_custom() throws Exception {
        Tested tested = new Tested(UHandlerIds.COLLECTION);
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("TestClass2", getClassSerializedData(TestClass2.class));
            put("InnerTC", getClassSerializedData(InnerTC.class));
        }};

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );

        TestClass2 instance = new TestClass2();
        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                instance,
                classNodes.get("TestClass2"),
                instanceContext
        ));
        tested.handle(instanceContext);

        System.out.println(instanceContext.getCollector().getNode());

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        collector.beginArray("list");
        collector.beginObject();
        collector.reset();

        Node node = instanceContext.getCollector().getNode();
        Node checkNode = collector.getNode();
        Assertions.assertThat(node.equals(checkNode)).isTrue();
    }

    private ObjectNode getClassSerializedData(Class<?> clazz) throws Exception {
        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class, Float.class, InnerTC.class)
                .build();
        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes()
                .setKeyArgumentsTypes()
                .setValueArgumentsTypes()
                .build();
        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(),
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
                clazz,
                new AnnotationExtractor()
        ));
        classProcessor.handle(classContext);

        return (ObjectNode) classContext.getCollector().detachNode();
    }

    private static class Tested extends InstanceCollectionTaskHandler {

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
        public Map<String, String> getAnnotationNames(String kind) {
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

    private static class EmptyValuesMemento implements InstanceContextStateMemento{
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
        public Map<String, String> getAnnotationNames(String kind) {
            return null;
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }
    }

    private static class InstanceContextDecorator implements InstanceContext{

        private final InstanceContext context;
        private final Tested tested;

        public InstanceContextDecorator(InstanceContext context, Tested tested) {
            this.context = context;
            this.tested = tested;
        }

        @Override
        public Map<String, ObjectNode> getClassNodes() {
            return context.getClassNodes();
        }

        @Override
        public Collector getCollector() {
            return context.getCollector();
        }

        @Override
        public CollectorPath getClassCollectorPath() {
            return context.getClassCollectorPath();
        }

        @Override
        public CollectorPath getMembersCollectorPath() {
            return context.getMembersCollectorPath();
        }

        @Override
        public ClassHeaderPartHandler getClassHeaderPartHandler() {
            return context.getClassHeaderPartHandler();
        }

        @Override
        public ClassMembersPartHandler getClassMembersPartHandler() {
            return context.getClassMembersPartHandler();
        }

        @Override
        public Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> getAnnotationExtractor() {
            return context.getAnnotationExtractor();
        }

        @Override
        public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            tested.handle(this);
        }

        @Override
        public ContextIds getContextIds() {
            return context.getContextIds();
        }

        @Override
        public ContextStateCareTaker<InstanceContextStateMemento> getContextStateCareTaker() {
            return context.getContextStateCareTaker();
        }
    }

    @SkeletonClass(name = "TestClass1")
    private static class TestClass1{

        @SkeletonMember
        private Set<Integer> intSet;

        @SkeletonMember
        private List<Float> floatList;

        public TestClass1(Set<Integer> intSet, List<Float> floatList) {
            this.intSet = intSet;
            this.floatList = floatList;
        }
    }

    @SkeletonClass(name = "TestClass2")
    private static class TestClass2{

        @SkeletonMember
        private List<InnerTC> list = new ArrayList<>(){{add(new InnerTC());}};
    }

    @SkeletonClass(name = "InnerTC")
    private static class InnerTC{

        @SkeletonMember
        private int intValue = 123;
    }
}
