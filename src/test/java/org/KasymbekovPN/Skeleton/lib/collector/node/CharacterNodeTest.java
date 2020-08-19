package org.KasymbekovPN.Skeleton.lib.collector.node;

//< restore
//import org.KasymbekovPN.Skeleton.lib.node.CharacterNode;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("CharacterNode. Testing of:")
//public class CharacterNodeTest {
//
//    @Test
//    @DisplayName("is-methods")
//    void testIsMethods(){
//        CharacterNode characterNode = new CharacterNode(null, '0');
//
//        assertThat(characterNode.isArray()).isFalse();
//        assertThat(characterNode.isObject()).isFalse();
//        assertThat(characterNode.isString()).isFalse();
//        assertThat(characterNode.isNumber()).isFalse();
//        assertThat(characterNode.isBoolean()).isFalse();
//        assertThat(characterNode.isCharacter()).isTrue();
//        assertThat(characterNode.isPrimitive()).isTrue();
//    }
//
//    @Test
//    @DisplayName("getParent method")
//    void testGetPatentMethod(){
//
//        ObjectNode parent = new ObjectNode(null);
//        CharacterNode child = new CharacterNode(parent, '0');
//
//        assertThat(child.getParent()).isEqualTo(parent);
//    }
//
//    static private Object[][] getDataForGetValueTesting(){
//        return new Object[][]{
//                {
//                        '0',
//                        '0',
//                        true
//                },
//                {
//                        '0',
//                        '1',
//                        false
//                }
//        };
//    }
//
//    @DisplayName("getValue method")
//    @ParameterizedTest
//    @MethodSource("getDataForGetValueTesting")
//    void testGetChildren(
//            char initValue,
//            char checkValue,
//            boolean result
//    ){
//        CharacterNode characterNode = new CharacterNode(null, initValue);
//        assertThat(characterNode.getValue().equals(checkValue)).isEqualTo(result);
//    }
//
//    @DisplayName("apply method")
//    @Test
//    void testApply(){
//        CharacterNode characterNode = new CharacterNode(null, '0');
//        TestCollectorProcess process = new TestCollectorProcess(characterNode);
//        TestCollectorProcess anotherProcess = new TestCollectorProcess(new CharacterNode(null, '0'));
//
//        characterNode.apply(process);
//        assertThat(process.isValid()).isTrue();
//
//        characterNode.apply(anotherProcess);
//        assertThat(anotherProcess.isValid()).isFalse();
//    }
//}
