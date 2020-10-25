package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SKClassContextStateMemento. Testing of:")
public class SKClassContextStateMementoTest {

    private static final String CLAZZ_IS_NULL = "The clazz is null";
    private static final String CLASS_HAS_NOT_ANNOTATION = "The clazz hasn't annotation";
    private static final String REMAINING_FIELDS_IS_EMPTY = "The remaining fields is empty";

    private static Object[][] getTestData(){
        return new Object[][]{
                {null, false, CLAZZ_IS_NULL},
                {TCWithoutClassAnnotation.class, false, CLASS_HAS_NOT_ANNOTATION},
                {TCWithoutFields.class, false, REMAINING_FIELDS_IS_EMPTY},
                {TCWithoutAnnotatedFields.class, false, REMAINING_FIELDS_IS_EMPTY},
                {TCWithAnnotations.class, true, ""}
        };
    }

    private static Object[][] getTestDataForGetClass(){
        return new Object[][]{
                {TCWithAnnotations.class}
        };
    }

    private static Object[][] getTestDataForGetRemainingFields(){
        Class<?>[] classes = {TCWithAnnotations.class};
        AnnotationExtractor extractor = new AnnotationExtractor();

        Object[][] objects = new Object[classes.length][2];
        for (int i = 0; i < classes.length; i++) {
            HashSet<Field> fields = new HashSet<>();
            Class<?> clazz = classes[i];
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                Optional<Annotation> maybeAnnotation
                        = extractor.extract(new MutablePair<>(SkeletonMember.class, field.getDeclaredAnnotations()));
                if (maybeAnnotation.isPresent()){
                    fields.add(field);
                }
            }

            objects[i][0] = clazz;
            objects[i][1] = fields;
        }

        return objects;
    }

    @DisplayName("validate method")
    @ParameterizedTest
    @MethodSource("getTestData")
    void testValidateMethod(Class<?> clazz, boolean success, String status) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        SKClassContextStateMemento mem = new SKClassContextStateMemento(clazz, new AnnotationExtractor());
        mem.validate();
        SimpleResult result = mem.getValidationResult();
        assertThat(result.isSuccess()).isEqualTo(success);
        assertThat(result.getStatus()).isEqualTo(status);
    }

    @DisplayName("getClazz method")
    @ParameterizedTest
    @MethodSource("getTestDataForGetClass")
    void testGetClazzMethod(Class<?> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        SKClassContextStateMemento mem = new SKClassContextStateMemento(clazz, new AnnotationExtractor());
        mem.validate();
        SimpleResult result = mem.getValidationResult();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getStatus()).isEmpty();
        assertThat(clazz).isEqualTo(mem.getClazz());
    }

    @DisplayName("getRemainingFields method")
    @ParameterizedTest
    @MethodSource("getTestDataForGetRemainingFields")
    void testGetRemainingFieldsMethod(Class<?> clazz, Set<Field> remainingFields) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        SKClassContextStateMemento mem
                = new SKClassContextStateMemento(clazz, new AnnotationExtractor());
        mem.validate();
        SimpleResult result = mem.getValidationResult();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getStatus()).isEmpty();
        assertThat(remainingFields).isEqualTo(mem.getRemainingFields());
    }

    private static class TCWithoutClassAnnotation {
    }

    @SkeletonClass(name = "TCWithoutFields")
    private static class TCWithoutFields{
    }

    @SkeletonClass(name = "TCWithoutAnnotatedFields")
    private static class TCWithoutAnnotatedFields{
        private int intValue;
    }

    @SkeletonClass(name = "TCWithAnnotations")
    private static class TCWithAnnotations{

        @SkeletonMember
        private int intValue;

        @SkeletonMember
        private Set<String> stringSet;
    }
}
