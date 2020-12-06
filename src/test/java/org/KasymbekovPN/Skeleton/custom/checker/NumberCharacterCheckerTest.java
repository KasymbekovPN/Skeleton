package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.custom.functional.checker.NumberCharacterChecker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;

@DisplayName("NumberCharacterChecker. Testing of:")
public class NumberCharacterCheckerTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {"A", false},
                {"a", false},
                {"B", false},
                {"b", false},
                {"C", false},
                {"c", false},
                {"D", false},
                {"d", false},
                {"E", false},
                {"e", false},
                {"F", false},
                {"f", false},
                {"G", false},
                {"g", false},
                {"H", false},
                {"h", false},
                {"I", false},
                {"i", false},
                {"J", false},
                {"j", false},
                {"K", false},
                {"k", false},
                {"L", false},
                {"l", false},
                {"M", false},
                {"m", false},
                {"N", false},
                {"n", false},
                {"O", false},
                {"o", false},
                {"P", false},
                {"p", false},
                {"Q", false},
                {"q", false},
                {"R", false},
                {"r", false},
                {"S", false},
                {"s", false},
                {"T", false},
                {"t", false},
                {"U", false},
                {"u", false},
                {"V", false},
                {"v", false},
                {"W", false},
                {"w", false},
                {"X", false},
                {"x", false},
                {"Y", false},
                {"y", false},
                {"Z", false},
                {"z", false},
                {"0", true},
                {"1", true},
                {"2", true},
                {"3", true},
                {"4", true},
                {"5", true},
                {"6", true},
                {"7", true},
                {"8", true},
                {"9", true},
        };
    }

    private static Function<Character, Boolean> checker;

    @BeforeAll
    static void init(){
        checker = new NumberCharacterChecker();
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(char ch, boolean result){
        Assertions.assertThat(checker.apply(ch)).isEqualTo(result);
    }
}
