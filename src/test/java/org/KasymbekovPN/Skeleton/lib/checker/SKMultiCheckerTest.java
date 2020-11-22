package org.KasymbekovPN.Skeleton.lib.checker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

@DisplayName("SKMultiChecker. Testing of:")
public class SKMultiCheckerTest {

    private static Object[][] getIntegerTestData(){
        return new Object[][]{
                {
                        "",
                        1,
                        true
                },
                {
                        "",
                        101,
                        false
                },
                {
                        "one",
                        101,
                        true
                },
                {
                        "one",
                        201,
                        false
                },
                {
                        "two",
                        201,
                        true
                },
                {
                        "two",
                        5,
                        false
                }
        };
    }

    private static Object[][] getCheckByAllTestData(){
        return new Object[][]{
                {
                        101,
                        new HashMap<String, Set<Integer>>(){{
                            put("zero", new HashSet<>(Arrays.asList(1,2,3)));
                            put("one", new HashSet<>(Arrays.asList(101,102,103)));
                        }},
                        "one",
                        true
                },
                {
                        2,
                        new HashMap<String, Set<Integer>>(){{
                            put("zero", new HashSet<>(Arrays.asList(1,2,3)));
                            put("one", new HashSet<>(Arrays.asList(101,102,103)));
                        }},
                        "zero",
                        true
                },
                {
                        0,
                        new HashMap<String, Set<Integer>>(){{
                            put("zero", new HashSet<>(Arrays.asList(1,2,3)));
                            put("one", new HashSet<>(Arrays.asList(101,102,103)));
                        }},
                        "",
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getIntegerTestData")
    void testInteger(String key, Integer checkedValue, boolean success){

        MultiChecker<String, Integer> multiChecker = new SKMultiChecker.Builder<String, Integer>(new SKSimpleChecker<Integer>(0, 1, 2, 3, 4, 5))
                .add("one", new SKSimpleChecker<Integer>(100, 101, 102, 103, 104, 105))
                .add("two", new SKSimpleChecker<Integer>(200, 201, 202, 203, 204, 205))
                .build();
        Assertions.assertThat(multiChecker.check(key, checkedValue)).isEqualTo(success);
    }

    @ParameterizedTest
    @MethodSource("getCheckByAllTestData")
    void testCheckByAll(Integer checkedValue, Map<String, Set<Integer>> initMap, String key, boolean isPresent){
        SKMultiChecker.Builder<String, Integer> builder = new SKMultiChecker.Builder<>(new SKSimpleChecker<>());
        for (Map.Entry<String, Set<Integer>> entry : initMap.entrySet()) {
            builder.add(entry.getKey(), new SKSimpleChecker<>(entry.getValue()));
        }
        MultiChecker<String, Integer> checker = builder.build();
        Optional<String> maybeKey = checker.checkByAll(checkedValue);
        boolean checkedIsPresent = maybeKey.isPresent();
        Assertions.assertThat(checkedIsPresent).isEqualTo(isPresent);
        if (checkedIsPresent){
            Assertions.assertThat(maybeKey.get()).isEqualTo(key);
        }
    }
}
