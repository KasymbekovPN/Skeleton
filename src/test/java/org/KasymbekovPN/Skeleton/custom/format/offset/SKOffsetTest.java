package org.KasymbekovPN.Skeleton.custom.format.offset;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("SKOffset. Testing of:")
public class SKOffsetTest {

    private static final String OFFSET_STEP = "    ";

    private static Object[][] getTestData(){
        return new Object[][]{
                {"11%offset%11", "22", "11    11", true},
                {"11%offset%11", "22", "11    11", false}
        };
    }

    @DisplayName("prepareTemplate method")
    @ParameterizedTest
    @MethodSource("getTestData")
    void testPrepareTemplate(String template, String checked, boolean result){
        SKOffset skOffset = new SKOffset(OFFSET_STEP);
        skOffset.inc();
        Assertions.assertThat(skOffset.prepareTemplate(template).equals(checked)).isEqualTo(result);
    }

    @DisplayName("inc/dec/get method")
    @Test
    void testIncDecGetMethod(){
        SKOffset skOffset = new SKOffset(OFFSET_STEP);
        for (int i = 0; i < 10; i++) {
            String offset = OFFSET_STEP.repeat(i);
            Assertions.assertThat(offset).isEqualTo(skOffset.get());
            skOffset.inc();
        }

        for (int i = 10; i >= 0; i--){
            String offset = OFFSET_STEP.repeat(i);
            Assertions.assertThat(offset).isEqualTo(skOffset.get());
            skOffset.dec();
        }
    }

    @DisplayName("reset method")
    @Test
    void testResetMethod(){
        for (int i = 1; i < 10; i++) {
            SKOffset skOffset = new SKOffset(OFFSET_STEP);
            for (int j = 0; j < i; j++) {
                skOffset.inc();
            }
            skOffset.reset();
            Assertions.assertThat(skOffset.get()).isEmpty();
        }
    }



//    @Override
//    public String prepareTemplate(String template) {
//        return template.replace(REPLACED_STRING, offset);
//    }

}
