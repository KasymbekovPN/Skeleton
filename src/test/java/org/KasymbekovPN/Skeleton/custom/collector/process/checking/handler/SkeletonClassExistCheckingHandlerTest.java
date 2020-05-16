package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            buffer = (SkeletonObjectNode) result.getChildren().get(pathItem);
        }
        return result;
    }

    private static class TestCollectorCheckingProcess implements CollectorCheckingProcess {

        private final Map<Class<? extends Node>, CollectorProcessHandler> handlers = new HashMap<>();
        private final Map<Class<? extends Node>, CollectorCheckingResult> results = new HashMap<>();

        @Override
        public void handle(Node node) {
            Class<? extends Node> clazz = node.getClass();
            if (handlers.containsKey(clazz)){
                handlers.get(clazz).handle(node);
            }
        }

        @Override
        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {
            handlers.put(clazz, collectorProcessHandler);
        }

        @Override
        public void setResult(Class<? extends Node> clazz, CollectorCheckingResult result) {
            results.put(clazz, result);
        }

        @Override
        public CollectorCheckingResult getResult() {
            if (results.containsValue(CollectorCheckingResult.EXCLUDE)){
                return CollectorCheckingResult.EXCLUDE;
            } else if (results.containsValue(CollectorCheckingResult.INCLUDE)) {
                return CollectorCheckingResult.INCLUDE;
            }
            return CollectorCheckingResult.NONE;
        }

    }
}
