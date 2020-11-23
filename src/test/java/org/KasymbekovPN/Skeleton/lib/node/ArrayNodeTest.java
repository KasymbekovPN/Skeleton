package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;

@DisplayName("ArrayNode. Testing of")
public class ArrayNodeTest {

    private static Object[][] getTestDataGetParent(){
        return new Object[][]{
                {null},
                {new ArrayNode(null)},
                {new ObjectNode(null)}
        };
    }

    private static Object[][] getTestDataEI(){
        return new Object[][]{
                {new ArrayNode(null), true},
                {new BooleanNode(null, true), false},
                {new CharacterNode(null, 'x'), false},
                {new InvalidNode(null, "", ""), false},
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), false}
        };
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        ArrayNode arrayNode = new ArrayNode(parent);
        Assertions.assertThat(arrayNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        ArrayNode arrayNode = new ArrayNode(null);
        Assertions.assertThat(arrayNode).isEqualTo(arrayNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        ArrayNode arrayNode = new ArrayNode(null);
        Assertions.assertThat(arrayNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("addChild - value is null")
    @Test
    void testAddChild_valueIsNull(){
        ArrayNode arrayNode = new ArrayNode(null);
        Optional<Node> maybeNode = arrayNode.addChild(null);
        Assertions.assertThat(maybeNode).isEmpty();
    }

    @DisplayName("addChild - value isn't null")
    @Test
    void testAddChild_valueIsNotNull(){
        ArrayNode arrayNode = new ArrayNode(null);
        StringNode stringNode = new StringNode(arrayNode, "123");
        Optional<Node> maybeNode = arrayNode.addChild(stringNode);
        Assertions.assertThat(maybeNode).isPresent();
        Assertions.assertThat(maybeNode.get()).isEqualTo(stringNode);
        Assertions.assertThat(arrayNode.getChildren().size()).isEqualTo(1);
        Assertions.assertThat(arrayNode.getChildren().get(0)).isEqualTo(stringNode);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        ArrayNode arrayNode = new ArrayNode(null);
        Assertions.assertThat(arrayNode.is(node.getEI())).isEqualTo(result);
    }
}
