package org.KasymbekovPN.Skeleton.lib.checker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @MethodSource("getIntegerTestData")
    void testInteger(String key, Integer checkedValue, boolean success){

        MultiChecker<String, Integer> multiChecker = new SKMultiChecker.Builder<String, Integer>(new SKSimpleChecker<Integer>(0, 1, 2, 3, 4, 5))
                .add("one", new SKSimpleChecker<Integer>(100, 101, 102, 103, 104, 105))
                .add("two", new SKSimpleChecker<Integer>(200, 201, 202, 203, 204, 205))
                .build();
        multiChecker.setKey(key);
        Assertions.assertThat(multiChecker.check(checkedValue)).isEqualTo(success);
    }
}
