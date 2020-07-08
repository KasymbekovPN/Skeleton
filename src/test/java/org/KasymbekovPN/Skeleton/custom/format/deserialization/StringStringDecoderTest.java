package org.KasymbekovPN.Skeleton.custom.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StringStringDecoder. Testing of:")
public class StringStringDecoderTest {

    private static Object[][] getTestDataForTestGetter(){
        return new Object[][]{
                {"Hello, world!!!"}
        };
    }

    @DisplayName("Getter method")
    @ParameterizedTest
    @MethodSource("getTestDataForTestGetter")
    void testGetter(String testString){
        StringDecoder decoder = new StringStringDecoder(testString);
        assertThat(decoder.getString()).isEqualTo(testString);
    }
}
