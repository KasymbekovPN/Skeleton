package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ClassSpecificClassHandler. Testing of:")
public class ClassSpecificClassHandlerTest {

    private static final String NOT_VALID = "not valid";
    private static final String NOT_CONTAIN_CLASS_PART = "Not contain class part";
    private static final String NO_ONE_SPECIFIC_FIELD = "No one specific ";

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
                        String type = field.getType().getTypeName();
                        int modifiers = field.getModifiers();
                        ArrayList<String> path = new ArrayList<>(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
                        path.add(field.getName());
                        ObjectNode target = (ObjectNode) collector.setTarget(path);

                        classMembersPartHandler.setKind(target, "specific");
                        classMembersPartHandler.setType(target, type);
                        classMembersPartHandler.setClassName(target, type);
                        classMembersPartHandler.setModifiers(target, modifiers);

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
    void testCheckMethodInvalidMemento() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        SKSimpleChecker<Class<?>> checker = new SKSimpleChecker<>(
                int.class
        );
        TestedClassWrapper tested = new TestedClassWrapper(checker, UHandlerIds.SPECIFIC);

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
    void testCheckMethodNotContainClassPart() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        SKSimpleChecker<Class<?>> checker = new SKSimpleChecker<>(
                int.class
        );
        TestedClassWrapper tested = new TestedClassWrapper(checker, UHandlerIds.SPECIFIC);

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
    void testCheckMethodNoONeSpecific() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        SKSimpleChecker<Class<?>> checker = new SKSimpleChecker<>(
                int.class
        );
        TestedClassWrapper tested = new TestedClassWrapper(checker, UHandlerIds.SPECIFIC);

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
        assertThat(result.getStatus()).isEqualTo(NO_ONE_SPECIFIC_FIELD);
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getTestDataFotDoItMethod")
    void testDoItMethod(ObjectNode objectNode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        SKSimpleChecker<Class<?>> checker = new SKSimpleChecker<>(
                int.class
        );
        TestedClassWrapper tested = new TestedClassWrapper(checker, UHandlerIds.SPECIFIC);

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.reset();

        ClassContext classContext = UClassSerialization.createClassContext(
                collector,
                new SKContextStateCareTaker<>()
        );

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

    private static class TestedClassWrapper extends ClassSpecificTaskHandler {

        public TestedClassWrapper(SimpleChecker<Class<?>> fieldChecker, String id) {
            super(fieldChecker, id);
            simpleResult = new SKSimpleResult();
        }

        public TestedClassWrapper(SimpleChecker<Class<?>> fieldChecker, String id, SimpleResult simpleResult) {
            super(fieldChecker, id, simpleResult);
        }

        @Override
        public void check(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void doIt(ClassContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
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
        private int intValue;
    }
}
