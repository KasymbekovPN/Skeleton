package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StringNode. Testing of:")
public class StringNodeTest {

    @Test
    @DisplayName("is-methods")
    void testIsMethods(){
        StringNode stringNode = new StringNode(null, "hello");

        assertThat(stringNode.isArray()).isFalse();
        assertThat(stringNode.isObject()).isFalse();
        assertThat(stringNode.isString()).isTrue();
        assertThat(stringNode.isNumber()).isFalse();
        assertThat(stringNode.isBoolean()).isFalse();
        assertThat(stringNode.isCharacter()).isFalse();
        assertThat(stringNode.isPrimitive()).isTrue();
    }

    @Test
    @DisplayName("getParent method")
    void testGetPatentMethod(){

        ObjectNode parent = new ObjectNode(null);
        StringNode child = new StringNode(parent, "hello");

        assertThat(child.getParent()).isEqualTo(parent);
    }

    static private Object[][] getDataForGetValueTesting(){
        return new Object[][]{
                {
                        "hello",
                        "hello",
                        true
                },
                {
                        "hello",
                        "world",
                        false
                }
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getDataForGetValueTesting")
    void testGetChildren(
            String initValue,
            String checkValue,
            boolean result
    ){
        StringNode stringNode = new StringNode(null, initValue);
        assertThat(stringNode.getValue().equals(checkValue)).isEqualTo(result);
    }

    @DisplayName("apply method")
    @Test
    void testApply(){
        StringNode characterNode = new StringNode(null, "hello");
        TestCollectorProcess process = new TestCollectorProcess(characterNode);
        TestCollectorProcess anotherProcess = new TestCollectorProcess(new StringNode(null, "hello"));

        characterNode.apply(process);
        assertThat(process.isValid()).isTrue();

        characterNode.apply(anotherProcess);
        assertThat(anotherProcess.isValid()).isFalse();
    }
}
