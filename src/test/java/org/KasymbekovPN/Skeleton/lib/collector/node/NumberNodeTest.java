package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NumberNode. Testing of:")
public class NumberNodeTest {

    @Test
    @DisplayName("is-methods")
    void testIsMethods(){
        NumberNode stringNode = new NumberNode(null, 0);

        assertThat(stringNode.isArray()).isFalse();
        assertThat(stringNode.isObject()).isFalse();
        assertThat(stringNode.isString()).isFalse();
        assertThat(stringNode.isNumber()).isTrue();
        assertThat(stringNode.isBoolean()).isFalse();
        assertThat(stringNode.isCharacter()).isFalse();
        assertThat(stringNode.isPrimitive()).isTrue();
    }

    @Test
    @DisplayName("getParent method")
    void testGetPatentMethod(){

        ObjectNode parent = new ObjectNode(null);
        NumberNode child = new NumberNode(parent, 0);

        assertThat(child.getParent()).isEqualTo(parent);
    }

    static private Object[][] getDataForGetValueTesting(){
        return new Object[][]{
                {
                        10,
                        10,
                        true
                },
                {
                        10,
                        11,
                        false
                }
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getDataForGetValueTesting")
    void testGetChildren(
            Number initValue,
            Number checkValue,
            boolean result
    ){
        NumberNode numberNode = new NumberNode(null, initValue);
        assertThat(numberNode.getValue().equals(checkValue)).isEqualTo(result);
    }

    @DisplayName("apply method")
    @Test
    void testApply(){
        NumberNode characterNode = new NumberNode(null, 111);
        TestCollectorProcess process = new TestCollectorProcess(characterNode);
        TestCollectorProcess anotherProcess = new TestCollectorProcess(new NumberNode(null, 111));

        characterNode.apply(process);
        assertThat(process.isValid()).isTrue();

        characterNode.apply(anotherProcess);
        assertThat(anotherProcess.isValid()).isFalse();
    }
}
