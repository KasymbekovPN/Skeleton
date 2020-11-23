package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

@DisplayName("ObjectNode. Testing of:")
public class ObjectNodeTest {

    private static Object[][] getTestDataGetParent(){
        return new Object[][]{
                {null},
                {new ArrayNode(null)},
                {new ObjectNode(null)}
        };
    }

    private static Object[][] getTestDataEI(){
        return new Object[][]{
                {new ArrayNode(null), false},
                {new BooleanNode(null, true), false},
                {new CharacterNode(null, 'x'), false},
                {new InvalidNode(null, "", ""), false},
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), true},
                {new StringNode(null, ""), false}
        };
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        ObjectNode objectNode = new ObjectNode(parent);
        Assertions.assertThat(objectNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        ObjectNode objectNode = new ObjectNode(null);
        Assertions.assertThat(objectNode).isEqualTo(objectNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        ObjectNode objectNode = new ObjectNode(null);
        Assertions.assertThat(objectNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("addChild - value is null")
    @Test
    void testAddChild_valueIsNull(){
        ObjectNode objectNode = new ObjectNode(null);
        Optional<Node> maybeNode = objectNode.addChild(null);
        Assertions.assertThat(maybeNode).isEmpty();
    }

    @DisplayName("addChild - value isn't null")
    @Test
    void testAddChild_valueIsNotNull(){
        ObjectNode objectNode = new ObjectNode(null);
        StringNode stringNode = new StringNode(objectNode, "123");
        Optional<Node> maybeNode = objectNode.addChild("str", stringNode);
        Assertions.assertThat(maybeNode).isPresent();
        Assertions.assertThat(maybeNode.get()).isEqualTo(stringNode);
        Assertions.assertThat(objectNode.getChildren().size()).isEqualTo(1);
        Assertions.assertThat(objectNode.getChildren().get("str")).isEqualTo(stringNode);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        ObjectNode objectNode = new ObjectNode(null);
        Assertions.assertThat(objectNode.is(node.getEI())).isEqualTo(result);
    }
}
