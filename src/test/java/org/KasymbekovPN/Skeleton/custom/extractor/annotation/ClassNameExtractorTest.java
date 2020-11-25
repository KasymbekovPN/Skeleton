package org.KasymbekovPN.Skeleton.custom.extractor.annotation;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

@DisplayName("ClassNameExtractor. Testing of:")
public class ClassNameExtractorTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {"WithAnnotation", WithAnnotation.class, true},
                {"WithAnnotation", WithoutAnnotation.class, false}
        };
    }

    private static ClassNameExtractor classNameExtractor;

    @BeforeAll
    static void init(){
        classNameExtractor = new ClassNameExtractor();
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(String name, Class<?> clazz, boolean result){
        Optional<String> maybeName = classNameExtractor.extract(clazz.getDeclaredAnnotations());

        boolean r = false;
        if (maybeName.isPresent()){
            r = maybeName.get().equals(name);
        }
        Assertions.assertThat(r).isEqualTo(result);
    }

    @SkeletonClass(name = "WithAnnotation")
    private static class WithAnnotation{}

    private static class WithoutAnnotation{}
}
