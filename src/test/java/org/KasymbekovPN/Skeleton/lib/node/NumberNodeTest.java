package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("NumberNode. Testing of:")
public class NumberNodeTest {

    private static Object[][] getTestDataGetValue(){
        return new Object[][]{
                {123, 123, true},
                {123, 321, false}
        };
    }

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
                {new NumberNode(null, 123.0), true},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), false}
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getTestDataGetValue")
    void testGetValueMethod(int init, int check, boolean result){
        NumberNode numberNode = new NumberNode(null, init);
        Assertions.assertThat(numberNode.getValue().equals(check)).isEqualTo(result);
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        NumberNode numberNode = new NumberNode(parent, 123);
        Assertions.assertThat(numberNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        NumberNode numberNode = new NumberNode(null, 1123);
        Assertions.assertThat(numberNode).isEqualTo(numberNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        NumberNode numberNode = new NumberNode(null, 123);
        Assertions.assertThat(numberNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        NumberNode numberNode = new NumberNode(null, 123);
        Assertions.assertThat(numberNode.is(node.getEI())).isEqualTo(result);
    }
}
