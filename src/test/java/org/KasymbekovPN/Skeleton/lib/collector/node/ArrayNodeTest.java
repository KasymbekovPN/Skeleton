package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayNode. Testing of:")
public class ArrayNodeTest {

    @Test
    @DisplayName("is-methods")
    void testIsMethods(){
        ArrayNode arrayNode = new ArrayNode(null);

        assertThat(arrayNode.isArray()).isTrue();
        assertThat(arrayNode.isObject()).isFalse();
        assertThat(arrayNode.isString()).isFalse();
        assertThat(arrayNode.isNumber()).isFalse();
        assertThat(arrayNode.isBoolean()).isFalse();
        assertThat(arrayNode.isCharacter()).isFalse();
        assertThat(arrayNode.isPrimitive()).isFalse();
    }

    @Test
    @DisplayName("getParent method")
    void testGetPatentMethod(){

        ArrayNode parent = new ArrayNode(null);
        ArrayNode child = new ArrayNode(parent);

        assertThat(child.getParent()).isEqualTo(parent);
    }

    static private Object[][] getDataForGetChildrenTesting(){
        return new Object[][]{
                {
                        new String[]{"value0", "value1", "value2"},
                        new String[]{"value0", "value1", "value2"},
                        true
                },
                {
                        new String[]{"value0", "value1", "value2"},
                        new String[]{"value0", "value12", "value2"},
                        false
                },
                {
                        new String[]{"value0", "value1", "value2"},
                        new String[]{"value0", "value1", "value2", "value3"},
                        false
                }
        };
    }

    @DisplayName("getChildren method")
    @ParameterizedTest
    @MethodSource("getDataForGetChildrenTesting")
    void testGetChildren(
            String[] valuesForCreation,
            String[] valuesForChecking,
            boolean result
    ){
        ArrayNode arrayNode = new ArrayNode(null);
        for (String value : valuesForCreation) {
            arrayNode.addChild(new StringNode(arrayNode, value));
        }

        boolean check;
        List<Node> children = arrayNode.getChildren();
        if (children.size() == valuesForChecking.length){
            check = true;

            for (int i = 0; i < valuesForChecking.length; i++) {
                if (children.get(i).isString()){
                    String value = ((StringNode) children.get(i)).getValue();
                    if (!value.equals(valuesForChecking[i])){
                        check = false;
                        break;
                    }
                } else {
                    check = false;
                    break;
                }
            }
        } else {
            check = false;
        }

        assertThat(check).isEqualTo(result);
    }

    @DisplayName("apply method")
    @Test
    void testApply(){
        ArrayNode arrayNode = new ArrayNode(null);
        TestCollectorProcess process = new TestCollectorProcess(arrayNode);
        TestCollectorProcess anotherProcess = new TestCollectorProcess(new ArrayNode(null));

        arrayNode.apply(process);
        assertThat(process.isValid()).isTrue();

        arrayNode.apply(anotherProcess);
        assertThat(anotherProcess.isValid()).isFalse();
    }
}
