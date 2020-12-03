package org.KasymbekovPN.Skeleton.lib.result;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

@DisplayName("SKSimpleResult. Testing of:")
public class SKSimpleResultTest {

    private static final String STATUS = "some failure status";

    private SimpleResult result;

    @BeforeEach
    void init(){
        result = new SKSimpleResult();
    }

    @DisplayName("is/setSuccess method")
    @Test
    void testIsSetSuccessMethod(){
        Assertions.assertThat(result.isSuccess()).isTrue();
        result.setSuccess(false);
        Assertions.assertThat(result.isSuccess()).isFalse();
    }

    @DisplayName("get/setStatus method")
    @Test
    void testGetSetStatusMethod(){
        Assertions.assertThat(result.getStatus()).isEmpty();
        result.setStatus(STATUS);
        Assertions.assertThat(result.getStatus()).isEqualTo(STATUS);
    }

    @DisplayName("setFailStatus/reset method")
    @Test
    void testSetFailStatusAndResetMethods(){
        Assertions.assertThat(result.isSuccess()).isTrue();
        Assertions.assertThat(result.getStatus()).isEmpty();
        result.setFailStatus(STATUS);
        Assertions.assertThat(result.isSuccess()).isFalse();
        Assertions.assertThat(result.getStatus()).isEqualTo(STATUS);
        result.reset();
        Assertions.assertThat(result.isSuccess()).isTrue();
        Assertions.assertThat(result.getStatus()).isEmpty();
    }

    @DisplayName("getResultData method")
    @Test
    void testGetResultDataMethod() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKResultData resultData = new SKResultData();
        SKSimpleResult simpleResult = new SKSimpleResult(resultData);
        Assertions.assertThat(simpleResult.getResultData()).isEqualTo(resultData);
    }
}
