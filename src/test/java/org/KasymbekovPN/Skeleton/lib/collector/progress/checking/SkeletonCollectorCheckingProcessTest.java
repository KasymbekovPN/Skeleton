package org.KasymbekovPN.Skeleton.lib.collector.progress.checking;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonCollectorCheckingProgress. Testing of:")
public class SkeletonCollectorCheckingProcessTest {

    private static Object[][] getTestDataForHandleAdd() {
        return new Object[][]{
                {
                        new HashMap<String, Pair>(){{
                            put("array", new Pair(ArrayNode.class, ArrayNode.class));
                            put("boolean", new Pair(BooleanNode.class, BooleanNode.class));
                            put("character", new Pair(CharacterNode.class, CharacterNode.class));
                            put("number", new Pair(NumberNode.class, NumberNode.class));
                            put("object", new Pair(ObjectNode.class, ObjectNode.class));
                            put("string", new Pair(StringNode.class, StringNode.class));
                        }},
                        true
                },
                {
                        new HashMap<String, Pair>(){{
                            put("array", new Pair(ArrayNode.class, ArrayNode.class));
                            put("boolean", new Pair(BooleanNode.class, BooleanNode.class));
                            put("character", new Pair(CharacterNode.class, CharacterNode.class));
                            put("number", new Pair(NumberNode.class, NumberNode.class));
                            put("object", new Pair(ObjectNode.class, ObjectNode.class));
                            put("string", new Pair(StringNode.class, NumberNode.class));
                        }},
                        false
                },
        };
    }

    @DisplayName(" handle and addHandler")
    @ParameterizedTest
    @MethodSource("getTestDataForHandleAdd")
    void testHandleAndAdd(
            Map<String, Pair> data,
            boolean result
    ){
        Map<String, Class<? extends Node>> results = new HashMap<>();
        SkeletonCollectorCheckingProcess process = new SkeletonCollectorCheckingProcess();
        for (Map.Entry<String, Pair> entry : data.entrySet()) {
            process.addHandler(entry.getValue().init, new NodeProcessHandler(entry.getKey(), results, process));
        }

        Collector collector = createCollector();
        collector.apply(process);

        boolean check = true;
        for (Map.Entry<String, Pair> entry : data.entrySet()) {
            String key = entry.getKey();
            if (results.containsKey(key)){
                if (!results.get(key).equals(entry.getValue().check)){
                    check = false;
                    break;
                } else {
                    results.remove(key);
                }
            } else {
                check = false;
                break;
            }
        }

        check &= results.size() == 0;

        assertThat(check).isEqualTo(result);
    }

    private static Collector createCollector(){
        Collector collector = new SkeletonCollector(new TestCollectorStructure(), false);

        collector.beginArray("array");
        collector.end();

        collector.addProperty("boolean", false);

        collector.addProperty("character", '0');

        collector.addProperty("number", 10);

        collector.addProperty("string", "hello");

        return collector;
    }

    private static class NodeProcessHandler implements CollectorProcessHandler {

        private final String name;
        private final Map<String,Class<? extends Node>> results;
        private final CollectorCheckingProcess process;

        public NodeProcessHandler(String name, Map<String, Class<? extends Node>> results, CollectorCheckingProcess process) {
            this.name = name;
            this.results = results;
            this.process = process;
        }

        @Override
        public void handle(Node node) {
            results.put(name, node.getClass());

            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                for (Map.Entry<String, Node> entry : objectNode.getChildren().entrySet()) {
                    entry.getValue().apply(process);
                }
            }
        }
    }

    private static class TestCollectorStructure implements CollectorStructure{
        @Override
        public List<String> getPath(EntityItem entityItem) {
            return null;
        }
    }

    private static class Pair{
        Class<? extends Node> init;
        Class<? extends Node> check;

        public Pair(Class<? extends Node> init, Class<? extends Node> check) {
            this.init = init;
            this.check = check;
        }
    }
}
