package org.KasymbekovPN.Skeleton.custom.extractor.annotation;

import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.apache.commons.lang3.tuple.MutablePair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.util.Optional;

@DisplayName("AnnotationExtractor. Testing of:")
public class AnnotationExtractorTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {SkeletonClass.class, WithAnnotation.class, true},
                {SkeletonClass.class, WithoutAnnotation.class, false}
        };
    }

    private static AnnotationExtractor annotationExtractor;

    @BeforeAll
    static void init(){
        annotationExtractor = new AnnotationExtractor();
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<? extends Annotation> annotation, Class<?> clazz, boolean result){
        Optional<Annotation> maybeAnnotation
                = annotationExtractor.apply(new MutablePair<>(annotation, clazz.getDeclaredAnnotations()));

        boolean r = false;
        if (maybeAnnotation.isPresent()){
            r = maybeAnnotation.get().annotationType().equals(annotation);
        }
        Assertions.assertThat(r).isEqualTo(result);
    }

    @SkeletonClass(name = "WithAnnotation")
    private static class WithAnnotation{
    }

    private static class WithoutAnnotation{}
}
