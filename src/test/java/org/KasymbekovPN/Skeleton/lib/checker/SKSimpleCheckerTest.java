package org.KasymbekovPN.Skeleton.lib.checker;

import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SKSimpleCheckerTest. Testing of:")
public class SKSimpleCheckerTest {

    private static Object[][] getIntegerTestData(){
        return new Object[][]{
                {
                        1,
                        new Integer[]{1, 2, 3},
                        true
                },
                {
                        10,
                        new Integer[]{100, 200, 300},
                        false
                }
        };
    }

    private static Object[][] getStringTestData(){
        return new Object[][]{
                {
                        "hello",
                        new String[]{"hello", "world"},
                        true
                },
                {
                        "!!!",
                        new String[]{"hello", "world"},
                        false
                }
        };
    }

    private static Object[][] getClassTestData(){
        return new Object[][]{
                {
                        Class1.class,
                        new Class[]{Class1.class, Class2.class},
                        true
                },
                {
                        Class3.class,
                        new Class[]{Class1.class, Class2.class},
                        false
                }
        };
    }

    @DisplayName("with Integer")
    @ParameterizedTest
    @MethodSource("getIntegerTestData")
    void testWithInteger(Integer testedValue, Integer[] allowedValues, boolean result){
        assertThat(new SKSimpleChecker<>(allowedValues).apply(testedValue)).isEqualTo(result);
    }

    @DisplayName("with String")
    @ParameterizedTest
    @MethodSource("getStringTestData")
    void testWithString(String testedValue, String[] allowedValues, boolean result){
        assertThat(new SKSimpleChecker<>(allowedValues).apply(testedValue)).isEqualTo(result);
    }

    @DisplayName("with Class")
    @ParameterizedTest
    @MethodSource("getClassTestData")
    void testWithClass(Class<?> testedValue, Class<?>[] allowedValues, boolean result){
        assertThat(new SKSimpleChecker<>(allowedValues).apply(testedValue)).isEqualTo(result);
    }

    private static class Class1{}
    private static class Class2{}
    private static class Class3{}
}
