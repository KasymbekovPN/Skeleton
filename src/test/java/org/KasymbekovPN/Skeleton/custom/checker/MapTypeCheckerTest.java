package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.custom.functional.checker.MapTypeChecker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

@DisplayName("MapTypeChecker. Testing of:")
public class MapTypeCheckerTest {

    private static Object[][] getTestData() throws NoSuchFieldException {

        Class<TC0> tc0Class = TC0.class;
        Field intByInt = tc0Class.getDeclaredField("intByInt");
        Field strByInt = tc0Class.getDeclaredField("strByInt");
        Field intByStr = tc0Class.getDeclaredField("intByStr");
        Field strByStr = tc0Class.getDeclaredField("strByStr");

        Function<Field, Boolean> allChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(Integer.class, String.class)
                .addValueArgumentTypes(Integer.class, String.class)
                .build();

        Function<Field, Boolean> intKeyChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(Integer.class)
                .addValueArgumentTypes(Integer.class, String.class)
                .build();

        Function<Field, Boolean> strKeyChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(String.class)
                .addValueArgumentTypes(Integer.class, String.class)
                .build();

        Function<Field, Boolean> noneKeyChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addValueArgumentTypes(Integer.class, String.class)
                .build();

        Function<Field, Boolean> intValueChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(Integer.class, String.class)
                .addValueArgumentTypes(Integer.class)
                .build();

        Function<Field, Boolean> strValueChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(Integer.class, String.class)
                .addValueArgumentTypes(String.class)
                .build();

        Function<Field, Boolean> noneValueChecker = new MapTypeChecker.Builder()
                .addTypes(Map.class)
                .addKeyArgumentTypes(Integer.class, String.class)
                .build();

        return new Object[][]{

                {allChecker, intByInt, true},
                {allChecker, intByStr, true},
                {allChecker, strByInt, true},
                {allChecker, strByStr, true},

                {intKeyChecker, intByInt, true},
                {intKeyChecker, intByStr, false},
                {intKeyChecker, strByInt, true},
                {intKeyChecker, strByStr, false},

                {strKeyChecker, intByInt, false},
                {strKeyChecker, intByStr, true},
                {strKeyChecker, strByInt, false},
                {strKeyChecker, strByStr, true},

                {noneKeyChecker, intByInt, false},
                {noneKeyChecker, intByStr, false},
                {noneKeyChecker, strByInt, false},
                {noneKeyChecker, strByStr, false},

                {intValueChecker, intByInt, true},
                {intValueChecker, intByStr, true},
                {intValueChecker, strByInt, false},
                {intValueChecker, strByStr, false},

                {strValueChecker, intByInt, false},
                {strValueChecker, intByStr, false},
                {strValueChecker, strByInt, true},
                {strValueChecker, strByStr, true},

                {noneValueChecker, intByInt, false},
                {noneValueChecker, intByStr, false},
                {noneValueChecker, strByInt, false},
                {noneValueChecker, strByStr, false}
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Function<Field, Boolean> checker, Field field, boolean result) {
        Assertions.assertThat(checker.apply(field)).isEqualTo(result);
    }

    private static class TC0{
        private Map<Integer, Integer> intByInt;
        private Map<Integer, String> strByInt;
        private Map<String, Integer> intByStr;
        private Map<String, String> strByStr;
    }
}
