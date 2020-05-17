package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonStringWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonStringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SkeletonStringWritingHandlerTest {
    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new HashMap<String, String>(){{
                            put("object1", "value1");
                            put("object2", "value2");
                            put("object3", "value3");
                            put("object4", "value4");
                            put("object5", "value5");
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void testLevel0(Map<String, String> objects) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithString(collector, objects);

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        new SkeletonObjectWritingHandler(process, SkeletonObjectNode.class);
        new SkeletonStringWritingHandler(process, SkeletonStringNode.class);
        collector.apply(process);

        assertThat(new SkeletonStringWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
