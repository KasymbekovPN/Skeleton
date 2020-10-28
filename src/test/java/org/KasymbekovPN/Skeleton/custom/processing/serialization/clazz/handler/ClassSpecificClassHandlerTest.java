package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ClassSpecificClassHandler. Testing of:")
public class ClassSpecificClassHandlerTest {

    private static final String NOT_VALID = "not valid";

//    private static Object[][] getTestDataFotDoItMethod(){
//
//        Class<?>[] classes = {ClassSignatureTaskHandlerTest.TestClass.class};
//        AnnotationExtractor annotationExtractor = new AnnotationExtractor();
//        SKCollector collector = new SKCollector();
//
//        Object[][] objects = new Object[classes.length][1];
//        for(int i = 0; i < classes.length; i++)
//        {
//            Class<?> clazz = classes[i];
//            Optional<Annotation> maybeAnnotation
//                    = annotationExtractor.extract(new MutablePair<>(SkeletonClass.class, clazz.getDeclaredAnnotations()));
//            String name = maybeAnnotation.map(annotation -> ((SkeletonClass) annotation).name()).orElse("");
//
//            collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
//            collector.addProperty("type", clazz.getTypeName());
//            collector.addProperty("modifiers", clazz.getModifiers());
//            collector.addProperty("name", name);
//
//            objects[i][0] = collector.detachNode();
//        }
//
//        return objects;
//    }

    @DisplayName("check method - invalid memento")
    @Test
    void testCheckMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
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



//
//    @DisplayName("doIt method")
//    @ParameterizedTest
//    @MethodSource("getTestDataFotDoItMethod")
//    void testDoItMethod(ObjectNode objectNode) throws ContextStateCareTakerIsEmpty, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        ClassSignatureTaskHandlerTest.TestedClassWrapper tested = new ClassSignatureTaskHandlerTest.TestedClassWrapper(UHandlerIds.SIGNATURE);
//
//        ClassContext classContext = UClassSerialization.createClassContext(
//                new SKCollector(),
//                new SKContextStateCareTaker<>()
//        );
//
//        classContext.getContextStateCareTaker().push(
//                new SKClassContextStateMemento(
//                        ClassSignatureTaskHandlerTest.TestClass.class,
//                        new AnnotationExtractor()
//                )
//        );
//        tested.check(classContext);
//
//        SimpleResult result = tested.getResult();
//        assertThat(result.isSuccess()).isTrue();
//
//        tested.doIt(classContext);
//        assertThat(classContext.getCollector().getNode()).isEqualTo(objectNode);
//    }

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

//    @SkeletonClass(name = "TestClass")
//    private static class TestClass{
//
//        @SkeletonMember
//        private int intValue;
//    }

}
