package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("InvalidNode. Testing of:")
public class InvalidNodeTest {

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
                {new InvalidNode(null, "", ""), true},
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), false}
        };
    }


    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        InvalidNode invalidNode = new InvalidNode(parent, "", "");
        Assertions.assertThat(invalidNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        InvalidNode invalidNode = new InvalidNode(null, "1", "2");
        Assertions.assertThat(invalidNode).isEqualTo(invalidNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        InvalidNode invalidNode = new InvalidNode(null, "", "");
        Assertions.assertThat(invalidNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        InvalidNode invalidNode = new InvalidNode(null, "", "");
        Assertions.assertThat(invalidNode.is(node.getEI())).isEqualTo(result);
    }
}
