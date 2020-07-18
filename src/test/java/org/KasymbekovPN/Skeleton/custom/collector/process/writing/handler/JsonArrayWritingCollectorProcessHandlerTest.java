package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonArrayWritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonObjectWritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonArrayWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonArrayWritingCollectorProcessHandlerTest {

    private static Object[][] getTestDataLevel0(){
        return new Object[][]{
                {
                        new HashSet<>(){{
                            add("object0");
                            add("object1");
                            add("object2");
                        }}
                },
                {
                        new HashSet<>(){{
                            add("object3");
                            add("object4");
                            add("object5");
                        }}
                },
                {
                        new HashSet<>(){{
                            add("object6");
                            add("object7");
                            add("object8");
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestDataLevel0")
    void testLevel0(Set<String> subArrayNames) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithArrayLevel0(collector, subArrayNames);

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
        new JsonArrayWritingCollectorProcessHandler(process, ArrayNode.class);
        collector.apply(process);

        assertThat(new SkeletonArrayWritingHandlerTestData(subArrayNames).check(process.getBuffer().toString())).isEqualTo(true);
    }

    private static Object[][] getTestDataLevel1(){
        return new Object[][]{
                {
                        new HashMap<String, Integer>(){{
                            put(
                                    "object0",
                                    3
                            );
                            put(
                                    "object1",
                                    5
                            );
                            put(
                                    "object2",
                                    0
                            );
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestDataLevel1")
    void testLevel1(Map<String, Integer> subArrayNames) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithArrayLevel1(collector, subArrayNames);

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
        new JsonArrayWritingCollectorProcessHandler(process, ArrayNode.class);
        collector.apply(process);

        assertThat(new SkeletonArrayWritingHandlerTestData(subArrayNames).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
