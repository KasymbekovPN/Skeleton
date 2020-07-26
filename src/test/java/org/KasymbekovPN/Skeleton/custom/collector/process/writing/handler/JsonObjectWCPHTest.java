package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonObjectWCPHTest {

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
    void testLevel0(Set<String> subObjectNames) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithObjectLevel0(collector, subObjectNames);

//        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
//        //<  !!! remake
////        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
//        collector.apply(process);
//
//        assertThat(new SkeletonObjectWritingHandlerTestData(subObjectNames).check(process.getBuffer().toString())).isEqualTo(true);
    }

    private static Object[][] getTestDataLevel1(){
        return new Object[][]{
                {
                    new HashMap<String, Set<String>>(){{
                        put(
                                "object0",
                                new HashSet<>(){{
                                    add("object0_1");
                                    add("object0_2");
                                }}
                        );
                        put(
                                "object1",
                                new HashSet<>(){{
                                    add("object1_1");
                                    add("object1_2");
                                    add("object1_3");
                                }}
                        );
                        put(
                                "object2",
                                new HashSet<>()
                        );
                    }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestDataLevel1")
    void testLevel1(Map<String, Set<String>> subObjectNames) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithObjectLevel1(collector, subObjectNames);

//        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
//        //< !!! remake
////        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
//        collector.apply(process);
//
//        assertThat(new SkeletonObjectWritingHandlerTestData(subObjectNames).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
