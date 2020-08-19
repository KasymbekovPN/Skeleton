package org.KasymbekovPN.Skeleton.lib.collector.node;

//< restore
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.lib.node.StringNode;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("ObjectNode. Testing of:")
//public class ObjectNodeTest {
//
//    @Test
//    @DisplayName("is-methods")
//    void testIsMethods(){
//        ObjectNode objectNode = new ObjectNode(null);
//
//        assertThat(objectNode.isArray()).isFalse();
//        assertThat(objectNode.isObject()).isTrue();
//        assertThat(objectNode.isString()).isFalse();
//        assertThat(objectNode.isNumber()).isFalse();
//        assertThat(objectNode.isBoolean()).isFalse();
//        assertThat(objectNode.isCharacter()).isFalse();
//        assertThat(objectNode.isPrimitive()).isFalse();
//    }
//
//    @Test
//    @DisplayName("getParent method")
//    void testGetPatentMethod(){
//
//        ObjectNode parent = new ObjectNode(null);
//        ObjectNode child = new ObjectNode(parent);
//
//        assertThat(child.getParent()).isEqualTo(parent);
//    }
//
//    static private Object[][] getDataForGetChildrenTesting(){
//        return new Object[][]{
//                {
//                        new String[]{"value0", "value1", "value2"},
//                        new String[]{"value0", "value1", "value2"},
//                        true
//                },
//                {
//                        new String[]{"value0", "value1", "value2"},
//                        new String[]{"value0", "value12", "value2"},
//                        false
//                },
//                {
//                        new String[]{"value0", "value1", "value2"},
//                        new String[]{"value0", "value1", "value2", "value3"},
//                        false
//                }
//        };
//    }
//
//    @DisplayName("getChildren method")
//    @ParameterizedTest
//    @MethodSource("getDataForGetChildrenTesting")
//    void testGetChildren(
//            String[] valuesForCreation,
//            String[] valuesForChecking,
//            boolean result
//    ){
//        ObjectNode objectNode = new ObjectNode(null);
//        for (String value : valuesForCreation) {
//            objectNode.addChild(value, new StringNode(objectNode, value));
//        }
//
//        boolean check;
//        Map<String, Node> children = objectNode.getChildren();
//        if (children.size() == valuesForChecking.length){
//            check = true;
//
//            for (String value : valuesForChecking) {
//                if (children.containsKey(value)) {
//                    StringNode stringNode = (StringNode) children.get(value);
//                    if (!stringNode.getValue().equals(value)) {
//                        check = false;
//                        break;
//                    }
//                } else {
//                    check = false;
//                    break;
//                }
//            }
//        } else {
//            check = false;
//        }
//
//        assertThat(check).isEqualTo(result);
//    }
//
//    @DisplayName("apply method")
//    @Test
//    void testApply(){
//        ObjectNode objectNode = new ObjectNode(null);
//        TestCollectorProcess process = new TestCollectorProcess(objectNode);
//        TestCollectorProcess anotherProcess = new TestCollectorProcess(new ObjectNode(null));
//
//        objectNode.apply(process);
//        assertThat(process.isValid()).isTrue();
//
//        objectNode.apply(anotherProcess);
//        assertThat(anotherProcess.isValid()).isFalse();
//    }
//}
