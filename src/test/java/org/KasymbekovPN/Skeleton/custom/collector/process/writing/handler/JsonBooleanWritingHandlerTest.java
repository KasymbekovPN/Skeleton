package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonBooleanWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonBooleanWritingHandlerTest {
    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new HashMap<String, Boolean>(){{
                            put("object1", true);
                            put("object2", false);
                            put("object3", true);
                            put("object4", false);
                            put("object5", true);
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void testLevel0(Map<String, Boolean> objects) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithBoolean(collector, objects);

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        //<
//        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
//        new JsonBooleanWritingHandler(process, BooleanNode.class);
        collector.apply(process);

        assertThat(new SkeletonBooleanWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
