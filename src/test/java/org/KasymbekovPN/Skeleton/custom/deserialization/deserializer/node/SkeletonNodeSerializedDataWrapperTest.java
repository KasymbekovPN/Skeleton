package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.custom.deserialization.node.deserializer.SkeletonNodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("SkeletonNodeSerializedDataWrapper. Testing of:")
public class SkeletonNodeSerializedDataWrapperTest {

    private static Object[][] getTestDataForTestNextSuccess() {
        return  new Object[][]{
                {"hello, world !!!"}
        };
    }

    @DisplayName("method 'next' (success)")
    @ParameterizedTest
    @MethodSource("getTestDataForTestNextSuccess")
    void testMethodNextSuccess(String testString){
        NodeSerializedDataWrapper dataWrapper = new SkeletonNodeSerializedDataWrapper(
                new StringStringDecoder(testString)
        );

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < testString.length(); i++) {
            sb.append(dataWrapper.next());
        }

        assertThat(sb.toString()).isEqualTo(testString);
    }

    private static Object[][] getTestDataForTestNextFail(){
        return new Object[][]{
                {"Hello, world !!!"}
        };
    }

    @DisplayName("method 'next' (fail)")
    @ParameterizedTest
    @MethodSource("getTestDataForTestNextFail")
    void testMethodNextFail(String testString){
        assertThat(
                catchThrowable(() -> {
                    NodeSerializedDataWrapper dataWrapper = new SkeletonNodeSerializedDataWrapper(
                            new StringStringDecoder(testString)
                    );

                    for (int i = 0; i < testString.length() + 1; i++) {
                        dataWrapper.next();
                    }
                })
        ).isInstanceOf(NoSuchElementException.class);
    }

    private static Object[][] getTestDataForTestHasNext(){
        String testString = "Hello, world!!!";
        int delta = 1;
        int size = testString.length() + delta;
        Object[][] objects = new Object[size][3];
        for (int i = 0; i < size; i++) {
            objects[i][0] = testString;
            objects[i][1] = i;
            objects[i][2] = testString.length() > i;
        }

        return objects;
    }

    @DisplayName("method 'hasNext'")
    @ParameterizedTest
    @MethodSource("getTestDataForTestHasNext")
    void testHasNext(String testString, int iterNumber, boolean result){
        NodeSerializedDataWrapper dataWrapper = new SkeletonNodeSerializedDataWrapper(
                new StringStringDecoder(testString)
        );

        boolean localResult = true;

        for (int i = 0; i <= iterNumber; i++) {
            localResult &= dataWrapper.hasNext();
            if (localResult){
                dataWrapper.next();
            }
        }

        assertThat(result).isEqualTo(localResult);
    }
}
