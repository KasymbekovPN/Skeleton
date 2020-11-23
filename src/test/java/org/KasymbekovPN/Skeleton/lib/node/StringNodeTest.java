package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("StringNode. Testing of:")
public class StringNodeTest {

    private static Object[][] getTestDataGetValue(){
        return new Object[][]{
                {"hello", "hello", true},
                {"hello", "world", false}
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
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), true}
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getTestDataGetValue")
    void testGetValueMethod(String init, String check, boolean result){
        StringNode stringNode = new StringNode(null, init);
        Assertions.assertThat(stringNode.getValue().equals(check)).isEqualTo(result);
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        StringNode stringNode = new StringNode(parent, "123");
        Assertions.assertThat(stringNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        StringNode stringNode = new StringNode(null, "hello");
        Assertions.assertThat(stringNode).isEqualTo(stringNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        StringNode stringNode = new StringNode(null, "123");
        Assertions.assertThat(stringNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        StringNode stringNode = new StringNode(null, "123");
        Assertions.assertThat(stringNode.is(node.getEI())).isEqualTo(result);
    }
}
