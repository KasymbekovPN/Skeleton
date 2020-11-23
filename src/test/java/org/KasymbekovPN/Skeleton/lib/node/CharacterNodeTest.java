package org.KasymbekovPN.Skeleton.lib.node;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("CharacterNode. Testing of:")
public class CharacterNodeTest {

    private static Object[][] getTestDataGetValue(){
        return new Object[][]{
                {'x', 'x', true},
                {'x', 'y', false}
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
                {new CharacterNode(null, 'x'), true},
                {new InvalidNode(null, "", ""), false},
                {new NumberNode(null, 123.0), false},
                {new ObjectNode(null), false},
                {new StringNode(null, ""), false}
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getTestDataGetValue")
    void testGetValueMethod(char init, char check, boolean result){
        CharacterNode characterNode = new CharacterNode(null, init);
        Assertions.assertThat(characterNode.getValue().equals(check)).isEqualTo(result);
    }

    @DisplayName("getParent method")
    @ParameterizedTest
    @MethodSource("getTestDataGetParent")
    void testGetParent(Node parent){
        CharacterNode characterNode = new CharacterNode(parent, 'x');
        Assertions.assertThat(characterNode.getParent()).isEqualTo(parent);
    }

    @DisplayName("deepCopy method")
    @Test
    void testDeepCopy(){
        CharacterNode characterNode = new CharacterNode(null, 'x');
        Assertions.assertThat(characterNode).isEqualTo(characterNode.deepCopy(null));
    }

    @DisplayName("getEI method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testGetEI(Node node, boolean result){
        CharacterNode characterNode = new CharacterNode(null, 'x');
        Assertions.assertThat(characterNode.getEI().equals(node.getEI())).isEqualTo(result);
    }

    @DisplayName("is method")
    @ParameterizedTest
    @MethodSource("getTestDataEI")
    void testIsMethod(Node node, boolean result){
        CharacterNode characterNode = new CharacterNode(null, 'x');
        Assertions.assertThat(characterNode.is(node.getEI())).isEqualTo(result);
    }
}
