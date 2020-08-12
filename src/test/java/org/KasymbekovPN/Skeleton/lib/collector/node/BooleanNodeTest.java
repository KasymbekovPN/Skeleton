package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BooleanNode. Testing of:")
public class BooleanNodeTest {

    @Test
    @DisplayName("is-methods")
    void testIsMethods(){
        BooleanNode booleanNode = new BooleanNode(null, false);

        assertThat(booleanNode.isArray()).isFalse();
        assertThat(booleanNode.isObject()).isFalse();
        assertThat(booleanNode.isString()).isFalse();
        assertThat(booleanNode.isNumber()).isFalse();
        assertThat(booleanNode.isBoolean()).isTrue();
        assertThat(booleanNode.isCharacter()).isFalse();
        assertThat(booleanNode.isPrimitive()).isTrue();
    }

    @Test
    @DisplayName("getParent method")
    void testGetPatentMethod(){

        ObjectNode parent = new ObjectNode(null);
        BooleanNode child = new BooleanNode(parent, false);

        assertThat(child.getParent()).isEqualTo(parent);
    }

    static private Object[][] getDataForGetValueTesting(){
        return new Object[][]{
                {
                        false,
                        false,
                        true
                },
                {
                        true,
                        false,
                        false
                },
                {
                        false,
                        true,
                        false
                },
                {
                        true,
                        true,
                        true
                }
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getDataForGetValueTesting")
    void testGetChildren(
            boolean initValue,
            boolean checkValue,
            boolean result
    ){
        BooleanNode booleanNode = new BooleanNode(null, initValue);
        assertThat(booleanNode.getValue().equals(checkValue)).isEqualTo(result);
    }

    @DisplayName("apply method")
    @Test
    void testApply(){
        BooleanNode booleanNode = new BooleanNode(null, false);
        TestCollectorProcess process = new TestCollectorProcess(booleanNode);
        TestCollectorProcess anotherProcess = new TestCollectorProcess(new BooleanNode(null, false));

        booleanNode.apply(process);
        assertThat(process.isValid()).isTrue();

        booleanNode.apply(anotherProcess);
        assertThat(anotherProcess.isValid()).isFalse();
    }
}
