package org.KasymbekovPN.Skeleton.lib.collector.progress.writing;

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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonCollectorWritingProcess. Testing of:")
public class SkeletonCollectorWritingProcessTest {
    private static Object[][] getTestDataForHandleAdd() {
        return new Object[][]{
                {
                        new HashMap<String, Pair<EntityItem, EntityItem>>(){{
                            put("array", new Pair<>(ArrayNode.ei(), ArrayNode.ei()));
                            put("boolean", new Pair<>(BooleanNode.ei(), BooleanNode.ei()));
                            put("character", new Pair<>(CharacterNode.ei(), CharacterNode.ei()));
                            put("number", new Pair<>(NumberNode.ei(), NumberNode.ei()));
                            put("object", new Pair<>(ObjectNode.ei(), ObjectNode.ei()));
                            put("string", new Pair<>(StringNode.ei(), StringNode.ei()));
                        }},
                        true
                },
                {
                        new HashMap<String, Pair<EntityItem, EntityItem>>(){{
                            put("array", new Pair<>(ArrayNode.ei(), ArrayNode.ei()));
                            put("boolean", new Pair<>(BooleanNode.ei(), BooleanNode.ei()));
                            put("character", new Pair<>(CharacterNode.ei(), CharacterNode.ei()));
                            put("number", new Pair<>(NumberNode.ei(), NumberNode.ei()));
                            put("object", new Pair<>(ObjectNode.ei(), ObjectNode.ei()));
                            put("string", new Pair<>(StringNode.ei(), NumberNode.ei()));
                        }},
                        false
                },
        };
    }

    @DisplayName(" handle and addHandler")
    @ParameterizedTest
    @MethodSource("getTestDataForHandleAdd")
    void testHandleAndAdd(
            Map<String, Pair<EntityItem, EntityItem>> data,
            boolean result
    ){
        Map<String, EntityItem> results = new HashMap<>();
        SkeletonCollectorCheckingProcess process = new SkeletonCollectorCheckingProcess();
        for (Map.Entry<String, Pair<EntityItem, EntityItem>> entry : data.entrySet()) {
            process.addHandler(entry.getValue().first, new NodeProcessHandler(entry.getKey(), results, process));
        }

        Collector collector = createCollector();
        collector.apply(process);

        boolean check = true;
        for (Map.Entry<String, Pair<EntityItem, EntityItem>> entry : data.entrySet()) {
            String key = entry.getKey();
            if (results.containsKey(key)){
                if (!results.get(key).equals(entry.getValue().second)){
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
        private final Map<String,EntityItem> results;
        private final CollectorCheckingProcess process;

        public NodeProcessHandler(String name, Map<String, EntityItem> results, CollectorCheckingProcess process) {
            this.name = name;
            this.results = results;
            this.process = process;
        }

        @Override
        public void handle(Node node) {
            results.put(name, node.getEI());

            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                for (Map.Entry<String, Node> entry : objectNode.getChildren().entrySet()) {
                    entry.getValue().apply(process);
                }
            }
        }
    }

    private static class TestCollectorStructure implements CollectorStructure {
        @Override
        public List<String> getPath(EntityItem entityItem) {
            return null;
        }
    }

    private static class Pair<T, K>{
        T first;
        K second;

        public Pair(T first, K second) {
            this.first = first;
            this.second = second;
        }
    }
}
