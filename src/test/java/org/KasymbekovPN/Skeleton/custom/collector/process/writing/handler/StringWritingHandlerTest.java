package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonStringWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StringWritingHandlerTest {
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
        new ObjectWritingHandler(process, ObjectNode.class);
        new StringWritingHandler(process, StringNode.class);
        collector.apply(process);

        assertThat(new SkeletonStringWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
