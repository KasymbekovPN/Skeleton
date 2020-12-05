package org.KasymbekovPN.Skeleton.lib.result;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;

@DisplayName("SKResultData. Testing of:")
public class SKResultDataTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {"id0", "hello", "id0", "hello", true, true},
                {"id0", "hello", "id0", "world", true, false},
                {"id0", "hello", "id1", "hello", false, false},
                {"id0", "hello", "id1", "world", false, false}
        };
    }

    private static Object[][] getClearMethodTestData(){
        return new Object[][]{
                {"id0", "hello"},
                {"id0", new Object()},
                {"id0", 123},
                {"id0", true},
                {"id0", new ArrayNode(null)}
        };
    }

    private ResultData resultData;

    @BeforeEach
    void init(){
        resultData = new SKResultData();
    }

    @DisplayName("set/get/contains methods")
    @ParameterizedTest
    @MethodSource("getTestData")
    void testSetGetContainsMethods(String id,
                                   Object object,
                                   String checkId,
                                   Object checkObject,
                                   boolean contains,
                                   boolean equals){
        resultData.set(id, object);
        Assertions.assertThat(resultData.contains(checkId, checkObject.getClass())).isEqualTo(contains);
        if (contains){
            Assertions.assertThat(resultData.get(checkId, checkObject.getClass()).equals(checkObject)).isEqualTo(equals);
        }
    }

    @DisplayName("clear method")
    @ParameterizedTest
    @MethodSource("getClearMethodTestData")
    void testClearMethod(String id, Object object){
        resultData.set(id, object);
        Assertions.assertThat(resultData.contains(id, object.getClass())).isTrue();

        resultData.clear();
        Assertions.assertThat(resultData.contains(id, object.getClass())).isFalse();
    }

    @DisplayName("get method - exception")
    @Test
    void testGetMethod_exception(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            resultData.get("id", Object.class);
        });
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }
}
