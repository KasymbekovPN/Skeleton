package org.KasymbekovPN.Skeleton.lib.collector.progress;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static Object[][] getTestDataBeginArray(){
        return new Object[][]{
                {"arrayProperty", "arrayProperty", true},
                {"arrayProperty", "notArrayProperty", false}
        };
    }

    @DisplayName(" arrayObject method")
    @ParameterizedTest
    @MethodSource("getTestDataBeginArray")
    void testBeginArray(
            String initPropertyName,
            String checkPropertyName,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.beginArray(initPropertyName);

        TestCollectorProcess process = new TestCollectorProcess(new ArrayObjectMethodHandler(checkPropertyName));
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static Object[][] getTestDataAddBoolProperty(){
        return new Object[][]{
                {"boolProperty", false, "boolProperty", false, true},
                {"boolProperty", false, "boolProperty", true, false},
                {"boolProperty", false, "noBoolProperty", false, false},
                {"boolProperty", false, "noBoolProperty", true, false},
                {"boolProperty", true, "boolProperty", false, false},
                {"boolProperty", true, "boolProperty", true, true},
                {"boolProperty", true, "noBoolProperty", false, false},
                {"boolProperty", true, "noBoolProperty", true, false}
        };
    }

    @DisplayName(" addBoolProperty")
    @ParameterizedTest
    @MethodSource("getTestDataAddBoolProperty")
    void testAddBoolProperty(
            String initProperty,
            boolean initValue,
            String checkProperty,
            boolean checkValue,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.addProperty(initProperty, initValue);

        TestCollectorProcess process = new TestCollectorProcess(
                new AddBoolPropertyMethodHandler(checkProperty, checkValue)
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static Object[][] getTestDataAddCharProperty(){
        return new Object[][]{
                {"charProperty", '0', "charProperty", '0', true},
                {"charProperty", '0', "charProperty", '1', false},
                {"charProperty", '0', "noCharProperty", '1', false},
                {"charProperty", '0', "noCharProperty", '1', false}
        };
    }

    @DisplayName(" addCharProperty")
    @ParameterizedTest
    @MethodSource("getTestDataAddCharProperty")
    void testAddCharProperty(
            String initProperty,
            Character initValue,
            String checkProperty,
            Character checkValue,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.addProperty(initProperty, initValue);

        TestCollectorProcess process = new TestCollectorProcess(
                new AddCharPropertyMethodHandler(checkProperty, checkValue)
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static Object[][] getTestDataAddNumberProperty(){
        return new Object[][]{
                {"numberProperty", 123, "numberProperty", 123, true},
                {"numberProperty", 123, "numberProperty", 321, false},
                {"numberProperty", 123, "noNumberProperty", 321, false},
                {"numberProperty", 123, "noNumberProperty", 321, false}
        };
    }

    @DisplayName(" addNumberProperty")
    @ParameterizedTest
    @MethodSource("getTestDataAddNumberProperty")
    void testAddNumberProperty(
            String initProperty,
            Number initValue,
            String checkProperty,
            Number checkValue,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.addProperty(initProperty, initValue);

        TestCollectorProcess process = new TestCollectorProcess(
                new AddNumberPropertyMethodHandler(checkProperty, checkValue)
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static Object[][] getTestDataAddStringProperty(){
        return new Object[][]{
                {"stringProperty", "hello", "stringProperty", "hello", true},
                {"stringProperty", "hello", "stringProperty", "world", false},
                {"stringProperty", "hello", "noStringProperty", "world", false},
                {"stringProperty", "hello", "noStringProperty", "world", false}
        };
    }

    @DisplayName(" addStringProperty")
    @ParameterizedTest
    @MethodSource("getTestDataAddStringProperty")
    void testAddStringProperty(
            String initProperty,
            String initValue,
            String checkProperty,
            String checkValue,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        collector.addProperty(initProperty, initValue);

        TestCollectorProcess process = new TestCollectorProcess(
                new AddStringPropertyMethodHandler(checkProperty, checkValue)
        );
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
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) { }

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
                Optional<Node> subNode = objectNode.get(checkPropertyName, ObjectNode.class);
                return subNode.isPresent();
            }
            return false;
        }
    }

    private static class ArrayObjectMethodHandler implements Handler{

        private final String checkPropertyName;

        public ArrayObjectMethodHandler(String checkPropertyName) {
            this.checkPropertyName = checkPropertyName;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                Optional<Node> subNode = objectNode.get(checkPropertyName, ArrayNode.class);
                return subNode.isPresent();
            }
            return false;
        }
    }

    private static class AddBoolPropertyMethodHandler implements Handler{

        private final String checkProperty;
        private final boolean checkValue;

        public AddBoolPropertyMethodHandler(String checkProperty, boolean checkValue) {
            this.checkProperty = checkProperty;
            this.checkValue = checkValue;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                Optional<Node> maybeBoolNode = objectNode.get(checkProperty, BooleanNode.class);
                if (maybeBoolNode.isPresent()){
                    return ((BooleanNode)maybeBoolNode.get()).getValue().equals(checkValue);
                }
            }
            return false;
        }
    }

    private static class AddCharPropertyMethodHandler implements Handler{

        private final String checkProperty;
        private final Character checkValue;

        public AddCharPropertyMethodHandler(String checkProperty, Character checkValue) {
            this.checkProperty = checkProperty;
            this.checkValue = checkValue;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                Optional<Node> maybeCharNode = objectNode.get(checkProperty, CharacterNode.class);
                if (maybeCharNode.isPresent()){
                    return ((CharacterNode)maybeCharNode.get()).getValue().equals(checkValue);
                }
            }
            return false;
        }
    }

    private static class AddNumberPropertyMethodHandler implements Handler{

        private final String checkProperty;
        private final Number checkValue;

        public AddNumberPropertyMethodHandler(String checkProperty, Number checkValue) {
            this.checkProperty = checkProperty;
            this.checkValue = checkValue;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                Optional<Node> maybeNumberNode = objectNode.get(checkProperty, NumberNode.class);
                if (maybeNumberNode.isPresent()){
                    return ((NumberNode)maybeNumberNode.get()).getValue().equals(checkValue);
                }
            }
            return false;
        }
    }

    private static class AddStringPropertyMethodHandler implements Handler{

        private final String checkProperty;
        private final String checkValue;

        public AddStringPropertyMethodHandler(String checkProperty, String checkValue) {
            this.checkProperty = checkProperty;
            this.checkValue = checkValue;
        }

        @Override
        public boolean handle(Node node) {
            if (node.isObject()){
                ObjectNode objectNode = (ObjectNode) node;
                Optional<Node> maybeStringNode = objectNode.get(checkProperty, StringNode.class);
                if (maybeStringNode.isPresent()){
                    return ((StringNode)maybeStringNode.get()).getValue().equals(checkValue);
                }
            }
            return false;
        }
    }
}
