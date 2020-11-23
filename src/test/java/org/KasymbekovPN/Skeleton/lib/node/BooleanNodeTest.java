package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("BooleanNode. Testing of:")
public class BooleanNodeTest {

    private static Object[][] getTestDataGetValue(){
        return new Object[][]{
                {false, false, true},
                {false, true, false},
                {true, false, false},
                {true, true, true}
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
                {new BooleanNode(null, true), true},
                {new CharacterNode(null, 'x'), false},
                {new InvalidNode(null, "", ""), false},
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), false}
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getTestDataGetValue")
    void testGetValueMethod(boolean init, boolean check, boolean result){
        BooleanNode booleanNode = new BooleanNode(null, init);
        Assertions.assertThat(booleanNode.getValue().equals(check)).isEqualTo(result);
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        BooleanNode booleanNode = new BooleanNode(parent, true);
        Assertions.assertThat(booleanNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        BooleanNode booleanNode = new BooleanNode(null, true);
        Assertions.assertThat(booleanNode).isEqualTo(booleanNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        BooleanNode booleanNode = new BooleanNode(null, true);
        Assertions.assertThat(booleanNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        BooleanNode booleanNode = new BooleanNode(null, true);
        Assertions.assertThat(booleanNode.is(node.getEI())).isEqualTo(result);
    }
}
