package org.KasymbekovPN.Skeleton.lib.collector.progress;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonCollector. Testing of")
public class SkeletonCollectorTest {

    private static Object[][] getTestDataBeginObject(){
        return new Object[][]{
                {"objectProperty", "objectProperty", true},
                {"objectProperty", "notObjectProperty", false}
        };
    }

    @DisplayName(" beginObject method")
    @ParameterizedTest
    @MethodSource("getTestDataBeginObject")
    void testBeginObject(
            String initPropertyName,
            String checkPropertyName,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.beginObject(initPropertyName);

        TestCollectorProcess process = new TestCollectorProcess(new BeginObjectMethodHandler(checkPropertyName));
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestCollectorProcess implements CollectorProcess{

        private final Handler handler;

        private boolean valid;

        public TestCollectorProcess(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void handle(Node node) {
            valid = handler.handle(node);
        }

        @Override
        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {}

        public boolean isValid() {
            return valid;
        }
    }

    private interface Handler{
        boolean handle(Node node);
    }

    private static class BeginObjectMethodHandler implements Handler{

        private final String checkPropertyName;

        public BeginObjectMethodHandler(String checkPropertyName) {
            this.checkPropertyName = checkPropertyName;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                return objectNode.containsKey(checkPropertyName);
            }
            return false;
        }
    }
}
