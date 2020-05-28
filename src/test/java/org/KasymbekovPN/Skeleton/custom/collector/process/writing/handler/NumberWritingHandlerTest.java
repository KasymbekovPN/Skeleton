package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonNumberWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberWritingHandlerTest {
    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new HashMap<String, Number>(){{
                            put("objectZ", 1);
                            put("objectY", -2.3);
                            put("objectX", -4.5f);
                            put("objectW", 67L);
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void testLevel0(Map<String, Number> objects) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithNumber(collector, objects);

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        new ObjectWritingHandler(process, ObjectNode.class);
        new NumberWritingHandler(process, NumberNode.class);
        collector.apply(process);

        assertThat(new SkeletonNumberWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}