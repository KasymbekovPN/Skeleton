package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.UCollectionTypeCheckerBuilder;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ClassContainerTaskHandler. Testing of:")
public class ClassContainerTaskHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NOT_CONTAIN_CLASS_PART = "Not contain class part";
    private static final String NO_ONE_CONTAINER_FIELD = "No one container field";

    private static Object[][] getTestDataFotDoItMethod(){

        Class<?>[] classes = {TestClass.class};
        AnnotationExtractor annotationExtractor = new AnnotationExtractor();
        SKCollector collector = new SKCollector();
        ClassMembersPartHandler classMembersPartHandler = USKClassMembersPartHandler.DEFAULT;

        Object[][] objects = new Object[classes.length][1];
        for(int i = 0; i < classes.length; i++)
        {
            collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
            collector.reset();

            Field[] declaredFields = classes[i].getDeclaredFields();
            for (Field field : declaredFields) {
                if (!Modifier.isStatic(field.getModifiers())){
                    Optional<Annotation> maybeAnnotation
                            = annotationExtractor.extract(new MutablePair<>(SkeletonMember.class, field.getAnnotations()));
                    if (maybeAnnotation.isPresent()){
                        String name = field.getName();
                        String type = field.getType().getCanonicalName();
                        int modifiers = field.getModifiers();

                        ArrayList<String> argumentTypes = new ArrayList<>();
                        Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                        for (Type actualTypeArgument : actualTypeArguments) {
                            argumentTypes.add(((Class<?>) actualTypeArgument).getCanonicalName());
                        }

                        ArrayList<String> path = new ArrayList<>(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
                        path.add(field.getName());
                        ObjectNode target = (ObjectNode) collector.setTarget(path);

                        classMembersPartHandler.setKind(target, "collection");
                        classMembersPartHandler.setType(target, type);
                        classMembersPartHandler.setClassName(target, type);
                        classMembersPartHandler.setModifiers(target, modifiers);
                        classMembersPartHandler.setArguments(target, argumentTypes);

                        collector.reset();
                    }
                }
            }
            objects[i][0] = collector.detachNode();
        }

        return objects;
    }

    @DisplayName("check method - invalid memento")
    @Test
    void testCheckMethodInvalidMemento() throws Exception {

        CollectionTypeChecker fieldChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class)
                .build();
        TestedClassWrapper tested = new TestedClassWrapper(fieldChecker, "collection");

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        classContext.getContextStateCareTaker().push(new NotValidMemento());
        tested.check(classContext);

        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("check method - not contain class part")
    @Test
    void testCheckMethodNotContainClassPart() throws Exception {
        CollectionTypeChecker fieldChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class)
                .build();
        TestedClassWrapper tested = new TestedClassWrapper(fieldChecker, "collection");

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        classContext.getContextStateCareTaker().push(new ValidMemento());
        tested.check(classContext);

        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NOT_CONTAIN_CLASS_PART);
    }

    @DisplayName("check method - no one specific")
    @Test
    void testCheckMethodNoONeSpecific() throws Exception {

        CollectionTypeChecker fieldChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class)
                .build();
        TestedClassWrapper tested = new TestedClassWrapper(fieldChecker, "collection");

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        Collector collector = classContext.getCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.reset();

        classContext.getContextStateCareTaker().push(new MementoWithoutFields());
        tested.check(classContext);

        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(NO_ONE_CONTAINER_FIELD);
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getTestDataFotDoItMethod")
    void testDoItMethod(ObjectNode objectNode) throws Exception {

        CollectionTypeChecker fieldChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class)
                .build();
        TestedClassWrapper tested = new TestedClassWrapper(fieldChecker, "collection");

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        Collector collector = classContext.getCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.reset();

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(TestClass.class, new AnnotationExtractor())
        );
        tested.check(classContext);

        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getStatus()).isEmpty();

        tested.doIt(classContext);
        assertThat(classContext.getCollector().getNode()).isEqualTo(objectNode);
    }

    private static class TestedClassWrapper extends ClassContainerTaskHandler {

        public TestedClassWrapper(Function<Field, Boolean> fieldChecker, String id) {
            super(fieldChecker, id);
            simpleResult = new SKSimpleResult();
        }

        public TestedClassWrapper(Function<Field, Boolean> fieldChecker, String id, SimpleResult simpleResult) {
            super(fieldChecker, id, simpleResult);
        }

        @Override
        protected void check(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        protected void doIt(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements ClassContextStateMemento {
        @Override
        public Class<?> getClazz() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Set<Field> getRemainingFields() {
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

    private static class ValidMemento implements ClassContextStateMemento{

        @Override
        public Class<?> getClazz() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Set<Field> getRemainingFields() {
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

    private static class MementoWithoutFields implements ClassContextStateMemento{
        @Override
        public Class<?> getClazz() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public Set<Field> getRemainingFields() {
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
    }
}
