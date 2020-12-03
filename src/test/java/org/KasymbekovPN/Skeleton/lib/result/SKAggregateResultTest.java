package org.KasymbekovPN.Skeleton.lib.result;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

@DisplayName("SKAggregateResult. Testing of:")
public class SKAggregateResultTest {

    private AggregateResult aggregateResult;

    @BeforeEach
    void init(){
        aggregateResult = new SKAggregateResult();
    }

    @DisplayName("put/get/has/reset methods")
    @Test
    void testPutGetMethods(){
        String id = "someId";
        SKSimpleResult skSimpleResult = new SKSimpleResult();
        aggregateResult.put(id, skSimpleResult);
        Assertions.assertThat(aggregateResult.has(id)).isTrue();
        Assertions.assertThat(skSimpleResult).isEqualTo(aggregateResult.get(id));

        aggregateResult.reset();
        Assertions.assertThat(aggregateResult.has(id)).isFalse();
    }

    @DisplayName("get method - exception")
    @Test
    void testGetMethod_exception(){
        Throwable throwable = Assertions.catchThrowable(
                () -> {
                    aggregateResult.get("id");
                }
        );
        Assertions.assertThat(throwable != null);
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }
}
