package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ClassSignatureTaskHandler. Testing of:")
public class ClassSignatureTaskHandlerTest {

    private static final String NOT_VALID = "not valid";

    private static Object[][] getTestDataFotDoItMethod(){

        Class<?>[] classes = {TestClass.class};
        AnnotationExtractor annotationExtractor = new AnnotationExtractor();
        SKCollector collector = new SKCollector();

        Object[][] objects = new Object[classes.length][1];
        for(int i = 0; i < classes.length; i++)
        {
            Class<?> clazz = classes[i];
            Optional<Annotation> maybeAnnotation
                    = annotationExtractor.apply(new MutablePair<>(SkeletonClass.class, clazz.getDeclaredAnnotations()));
            String name = maybeAnnotation.map(annotation -> ((SkeletonClass) annotation).name()).orElse("");

            collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
            collector.addProperty("type", clazz.getTypeName());
            collector.addProperty("modifiers", clazz.getModifiers());
            collector.addProperty("name", name);

            objects[i][0] = collector.detachNode();
        }

        return objects;
    }

    @DisplayName("check method")
    @Test
    void testCheckMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        TestedClassWrapper tested = new TestedClassWrapper(UHandlerIds.SIGNATURE);

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

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getTestDataFotDoItMethod")
    void testDoItMethod(ObjectNode objectNode) throws ContextStateCareTakerIsEmpty, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestedClassWrapper tested = new TestedClassWrapper(UHandlerIds.SIGNATURE);

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        TestClass.class,
                        new AnnotationExtractor()
                )
        );
        tested.check(classContext);

        SimpleResult result = tested.getResult();
        assertThat(result.isSuccess()).isTrue();

        tested.doIt(classContext);
        assertThat(classContext.getCollector().getNode()).isEqualTo(objectNode);
    }

    private static class TestedClassWrapper extends ClassSignatureTaskHandler{

        public TestedClassWrapper(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        public TestedClassWrapper(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
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

    private static class NotValidMemento implements ClassContextStateMemento{
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

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private int intValue;
    }
}
