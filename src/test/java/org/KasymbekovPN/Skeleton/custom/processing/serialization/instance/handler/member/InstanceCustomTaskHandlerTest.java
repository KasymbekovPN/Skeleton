package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.functional.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
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
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@DisplayName("InstanceCustomTaskHandler. Testing of:")
public class InstanceCustomTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String VALUES_IS_EMPTY = "Values by '%s' are empty";
    private static final String ANNOTATION_NAMES_IS_EMPTY = "Annotation names by %s are empty ";

    private static Object[][] getDoItMethodTestData(){
        return new Object[][]{
                {new String[]{"firstInnerTC", "secondInnerTC"}, true},
                {new String[]{"firstInnerTC1", "secondInnerTC1"}, false},
                {new String[]{"firstInnerTC"}, false},
                {new String[]{"firstInnerTC", "secondInnerTC", "thirdInnerTC"}, false}
        };
    }

    @DisplayName("check method - valid")
    @Test
    void testCheckMethod_valid() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.CUSTOM);
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
        Tested tested = new Tested(UHandlerIds.CUSTOM);
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        instanceContext.getContextStateCareTaker().push(new EmptyValuesMemento());
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isFalse();
        Assertions.assertThat(tested.getResult().getStatus()).isEqualTo(String.format(VALUES_IS_EMPTY, UHandlerIds.CUSTOM));
    }

    @DisplayName("check method - empty names")
    @Test
    void testCheckMethod_emptyNames() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.CUSTOM);
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                new HashMap<>(),
                new SKCollector()
        );
        instanceContext.getContextStateCareTaker().push(new EmptyNamesMemento());
        tested.check(instanceContext);

        Assertions.assertThat(tested.getResult().isSuccess()).isFalse();
        Assertions.assertThat(tested.getResult().getStatus()).isEqualTo(String.format(ANNOTATION_NAMES_IS_EMPTY, UHandlerIds.CUSTOM));
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getDoItMethodTestData")
    void testDoItMethod(String[] memberNames, boolean result) throws Exception {
        Tested tested = new Tested(UHandlerIds.CUSTOM);
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("TestClass", getClassSerializedData(TestClass.class));
            put("FirstInnerTC", getClassSerializedData(FirstInnerTC.class));
            put("SecondInnerTC", getClassSerializedData(SecondInnerTC.class));
        }};

        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );

        InstanceContextDecorator decorator = new InstanceContextDecorator(instanceContext, tested);

        TestClass instance = new TestClass();
        decorator.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                instance,
                classNodes.get("TestClass"),
                decorator
        ));
        tested.handle(decorator);

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        for (String memberName : memberNames) {
            collector.beginObject(memberName);
            collector.end();
        }
        collector.reset();

        Node node = decorator.getCollector().getNode();
        Node checkNode = collector.getNode();
        Assertions.assertThat(node.equals(checkNode)).isEqualTo(result);
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
                new SKSimpleChecker<Class<?>>(int.class, float.class),
                new SKSimpleChecker<String>("FirstInnerTC", "SecondInnerTC"),
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

    private static class Tested extends InstanceCustomTaskHandler {

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

    private static class NotValidMemento implements InstanceContextStateMemento{
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

    private static class EmptyNamesMemento implements InstanceContextStateMemento{
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
            return new HashMap<>(){{
                put("name", new Object());
            }};
        }

        @Override
        public Map<String, String> getAnnotationNames(String kind) {
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
        public OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> getAnnotationExtractor() {
            return context.getAnnotationExtractor();
        }

        @Override
        public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            tested.handle(this);
        }

        @Override
        public ContextIds getContextIds() throws ContextStateCareTakerIsEmpty {
            return context.getContextIds();
        }

        @Override
        public ContextStateCareTaker<InstanceContextStateMemento> getContextStateCareTaker() {
            return context.getContextStateCareTaker();
        }
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember(name = "FirstInnerTC")
        private FirstInnerTC firstInnerTC = new FirstInnerTC();

        @SkeletonMember(name = "SecondInnerTC")
        private SecondInnerTC secondInnerTC = new SecondInnerTC();
    }

    @SkeletonClass(name = "FirstInnerTC")
    private static class FirstInnerTC{

        @SkeletonMember
        private int intValue = 123;
    }

    @SkeletonClass(name = "SecondInnerTC")
    private static class SecondInnerTC{

        @SkeletonMember
        private float floatValue =0.256f;
    }
}
