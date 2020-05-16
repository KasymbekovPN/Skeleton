package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SkeletonClassExistCheckingHandlerTest {

    static private Object[][] getTestData(){
        return new Object[][]{
                {
                    new ArrayList<>(){{
                        add("class");
                    }},
                    new ArrayList<>(){{
                        add("class");
                    }},
                    CollectorCheckingResult.INCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("class");
                        }},
                        new ArrayList<>(){{
                            add("wrongClass");
                        }},
                        CollectorCheckingResult.EXCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("class");
                            add("sub");
                        }},
                        new ArrayList<>(){{
                            add("class");
                            add("sub");
                        }},
                        CollectorCheckingResult.INCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("class");
                        }},
                        new ArrayList<>(){{
                            add("class");
                            add("sub");
                        }},
                        CollectorCheckingResult.INCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("class");
                            add("sub");
                        }},
                        new ArrayList<>(){{
                            add("class");
                        }},
                        CollectorCheckingResult.EXCLUDE
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(List<String> checkArray, List<String> initArray, CollectorCheckingResult result){

        TestCollectorCheckingProcess process = new TestCollectorCheckingProcess();
        SkeletonClassExistCheckingHandler handler = new SkeletonClassExistCheckingHandler(process, SkeletonObjectNode.class, checkArray);
        handler.handle(
                create(initArray)
        );

        assertThat(process.getResult()).isEqualTo(result);
    }

    private SkeletonObjectNode create(List<String> path){

        SkeletonObjectNode result = new SkeletonObjectNode(null);
        SkeletonObjectNode buffer = result;
        for (String pathItem : path) {
            buffer.getChildren().put(pathItem, new SkeletonObjectNode(buffer));
            buffer = (SkeletonObjectNode) buffer.getChildren().get(pathItem);
        }
        return result;
    }
}
