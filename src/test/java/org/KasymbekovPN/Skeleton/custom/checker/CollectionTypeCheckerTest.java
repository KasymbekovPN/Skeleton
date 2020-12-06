package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.custom.functional.checker.CollectionTypeChecker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@DisplayName("CollectionTypeChecker. Testing of:")
public class CollectionTypeCheckerTest {

    private static Object[][] getTestData() throws NoSuchFieldException {

        Class<TC0> tc0Class = TC0.class;
        Field intSetField = tc0Class.getDeclaredField("intSet");
        Field intListField = tc0Class.getDeclaredField("intList");
        Field stringSetField = tc0Class.getDeclaredField("stringSet");
        Field stringListField = tc0Class.getDeclaredField("stringList");

        Function<Field, Boolean> allChecker = new CollectionTypeChecker.Builder()
                .addTypes(Set.class, List.class)
                .addArguments(Integer.class, String.class)
                .build();
        Function<Field, Boolean> setChecker = new CollectionTypeChecker.Builder()
                .addTypes(Set.class)
                .addArguments(Integer.class, String.class)
                .build();
        Function<Field, Boolean> listChecker = new CollectionTypeChecker.Builder()
                .addTypes(List.class)
                .addArguments(Integer.class, String.class)
                .build();
        Function<Field, Boolean> intChecker = new CollectionTypeChecker.Builder()
                .addTypes(Set.class, List.class)
                .addArguments(Integer.class)
                .build();
        Function<Field, Boolean> strChecker = new CollectionTypeChecker.Builder()
                .addTypes(Set.class, List.class)
                .addArguments(String.class)
                .build();

        return new Object[][]{

                {allChecker, intSetField, true},
                {allChecker, intListField, true},
                {allChecker, stringSetField, true},
                {allChecker, stringListField, true},

                {setChecker, intSetField, true},
                {setChecker, intListField, false},
                {setChecker, stringSetField, true},
                {setChecker, stringListField, false},

                {listChecker, intSetField, false},
                {listChecker, intListField, true},
                {listChecker, stringSetField, false},
                {listChecker, stringListField, true},

                {intChecker, intSetField, true},
                {intChecker, intListField, true},
                {intChecker, stringSetField, false},
                {intChecker, stringListField, false},

                {strChecker, intSetField, false},
                {strChecker, intListField, false},
                {strChecker, stringSetField, true},
                {strChecker, stringListField, true},
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Function<Field, Boolean> checker, Field field, boolean result){
        Assertions.assertThat(checker.apply(field)).isEqualTo(result);
    }

    private static class TC0{
        private Set<Integer> intSet;
        private List<Integer> intList;
        private Set<String> stringSet;
        private List<String> stringList;
    }
}
