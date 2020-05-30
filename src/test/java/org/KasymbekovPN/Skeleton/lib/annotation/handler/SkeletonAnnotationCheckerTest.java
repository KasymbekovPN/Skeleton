package org.KasymbekovPN.Skeleton.lib.annotation.handler;

import org.KasymbekovPN.Skeleton.lib.annotation.handler.annotations.TestAnnotation;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.annotations.TestOtherAnnotation;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.annotations.TestSubAnnotation;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.classes.TC0;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.classes.TC1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonAnnotationChecker. Testing of:")
public class SkeletonAnnotationCheckerTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {TC0.class, TestAnnotation.class, true},
                {TC1.class, TestOtherAnnotation.class, false},
                {TC0.class, TestOtherAnnotation.class, false},
                {TC1.class, TestAnnotation.class, false}
        };
    }

    @DisplayName("method check")
    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<?> clazz, Class<? extends Annotation> annotationClazz, boolean result){
        SkeletonAnnotationChecker sac = new SkeletonAnnotationChecker();
        Optional<Annotation> maybeAnnotation = sac.check(
                clazz.getDeclaredAnnotations(),
                annotationClazz
        );
        assertThat(maybeAnnotation.isPresent()).isEqualTo(result);
    }

    private static Object[][] getTestDataWithAnnotatingType(){
        return new Object[][]{
                {TC1.class, TestSubAnnotation.class, TestAnnotation.class, true},
                {TC1.class, TestSubAnnotation.class, TestOtherAnnotation.class, false},
                {TC1.class, TestOtherAnnotation.class, TestAnnotation.class, false},
        };
    }

    @DisplayName("method check with annotating type")
    @ParameterizedTest
    @MethodSource("getTestDataWithAnnotatingType")
    void testWithAnnotatingType(
            Class<?> clazz,
            Class<? extends Annotation> annotationClazz,
            Class<? extends Annotation> annotatingClazz,
            boolean result
    ){
        Optional<Annotation> maybeAnnotation = new SkeletonAnnotationChecker().check(
                clazz.getDeclaredAnnotations(),
                annotationClazz,
                annotatingClazz
        );
        assertThat(maybeAnnotation.isPresent()).isEqualTo(result);
    }
}
