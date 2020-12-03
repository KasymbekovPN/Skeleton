package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.lib.node.*;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("SKCollector. Testing of:")
public class SKCollectorTest {

    private static Object[][] getAddPropertyTestData(){
        return new Object[][]{
                {"booleanProperty", true, new BooleanNode(null, true)},
                {"characterProperty", 'x', new CharacterNode(null, 'x')},
                {"numberProperty", 123.456, new NumberNode(null, 123.456)},
                {"stringProperty", "hello", new StringNode(null, "hello")}
        };
    }

    private Collector collector;

    @BeforeEach
    void init(){
        collector = new SKCollector();
    }

    @DisplayName("clear method")
    @Test
    void testClearMethod(){
        collector.addProperty("test", "test");

        ObjectNode dummy = new ObjectNode(null);
        Pair<Node, Node> old = collector.attach(dummy, dummy);
        Assertions.assertThat(old.getLeft()).isNotEqualTo(new ObjectNode(null));
        Assertions.assertThat(old.getRight()).isNotEqualTo(new ObjectNode(null));

        collector.attach(old.getLeft(), old.getRight());
        collector.clear();
        old = collector.attach(dummy, dummy);
        Assertions.assertThat(old.getLeft()).isEqualTo(new ObjectNode(null));
        Assertions.assertThat(old.getRight()).isEqualTo(new ObjectNode(null));
    }

    @DisplayName("reset method")
    @Test
    void testResetMethod(){
        collector.addProperty("test", "test");

        ObjectNode dummy = new ObjectNode(null);
        Pair<Node, Node> old = collector.attach(dummy, dummy);
        collector.attach(old.getLeft(), old.getRight());
        Assertions.assertThat(old.getLeft()).isEqualTo(old.getRight());

        collector.beginObject("obj");
        old = collector.attach(dummy, dummy);
        collector.attach(old.getLeft(), old.getRight());
        Assertions.assertThat(old.getLeft()).isNotEqualTo(old.getRight());

        collector.reset();
        old = collector.attach(dummy, dummy);
        collector.attach(old.getLeft(), old.getRight());
        Assertions.assertThat(old.getLeft()).isEqualTo(old.getRight());
    }

    @DisplayName("beginObject - in object")
    @Test
    void testBeginObject_inObject(){
        collector.beginObject("innerObject");
        collector.end();
        Node node = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        checkNode.getChildren().put("innerObject", new ObjectNode(checkNode));

        Assertions.assertThat(node).isEqualTo(checkNode);
    }

    @DisplayName("beginObject - in array")
    @Test
    void testBeginObject_inArray(){
        collector.beginArray("array");
        collector.beginObject();
        collector.end();
        collector.end();
        Node node = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        ArrayNode arrayNode = new ArrayNode(null);
        arrayNode.getChildren().add(new ObjectNode(arrayNode));
        checkNode.getChildren().put("array", arrayNode);

        Assertions.assertThat(node).isEqualTo(checkNode);
    }

    @DisplayName("arrayBegin - in object")
    @Test
    void testArrayBegin_inObject(){
        collector.beginArray("array");
        Node collectorNode = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        checkNode.getChildren().put("array", new ArrayNode(null));

        Assertions.assertThat(collectorNode).isEqualTo(checkNode);
    }

    @DisplayName("beginArray - in array")
    @Test
    void testBeginArray_inArray(){
        collector.beginArray("array");
        collector.beginArray();
        collector.end();
        collector.end();
        Node node = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        ArrayNode arrayNode = new ArrayNode(null);
        arrayNode.getChildren().add(new ArrayNode(arrayNode));
        checkNode.getChildren().put("array", arrayNode);

        Assertions.assertThat(node).isEqualTo(checkNode);
    }

    @DisplayName("addProperty - in object")
    @ParameterizedTest
    @MethodSource("getAddPropertyTestData")
    void testAddProperty_inObject(String property,
                                  Object value,
                                  Node node){

        if (value.getClass().equals(Boolean.class)){
            collector.addProperty(property, (Boolean) value);
        } else if (value.getClass().equals(Character.class)){
            collector.addProperty(property, (Character) value);
        } else if (value.getClass().equals(String.class)){
            collector.addProperty(property, (String) value);
        } else {
            collector.addProperty(property, (Number) value);
        }
        Node collectorNode = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        checkNode.getChildren().put(property, node.deepCopy(checkNode));

        Assertions.assertThat(collectorNode).isEqualTo(checkNode);
    }

    @DisplayName("addProperty - in array")
    @ParameterizedTest
    @MethodSource("getAddPropertyTestData")
    void testAddProperty_inArray(String property,
                                  Object value,
                                  Node node){

        collector.beginArray("array");
        if (value.getClass().equals(Boolean.class)){
            collector.addProperty((Boolean) value);
        } else if (value.getClass().equals(Character.class)){
            collector.addProperty((Character) value);
        } else if (value.getClass().equals(String.class)){
            collector.addProperty((String) value);
        } else {
            collector.addProperty((Number) value);
        }
        collector.end();
        Node collectorNode = collector.getNode();

        ObjectNode checkNode = new ObjectNode(null);
        ArrayNode arrayNode = new ArrayNode(checkNode);
        checkNode.getChildren().put("array", arrayNode);
        arrayNode.getChildren().add(node.deepCopy(arrayNode));

        Assertions.assertThat(collectorNode).isEqualTo(checkNode);
    }
}
