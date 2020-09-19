package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

//< del
//import org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json.JsonNodeDeserializerHandler;
//import org.KasymbekovPN.Skeleton.custom.deserialization.node.deserializer.SkeletonNodeDeserializer;
//import org.KasymbekovPN.Skeleton.custom.deserialization.node.deserializer.SkeletonNodeSerializedDataWrapper;
//import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
//import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeDeserializer;
//import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
//import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//@DisplayName("SkeletonNodeDeserializer. Testing of:")
//public class SkeletonNodeDeserializerTest {
//
//    @Test
//    void test() throws Exception {
//
//        String testString = "{\"strProp\" : \"hello\", \"objProp\" : {\"innerInt\" : 434},  \"property1\" : true, \"intProp\" : 123, \"charProp\" : 'x', \"arrProperty\" : [[true, 1213],\"str1\",{\"innerProp1\":true},true,'y', false, 12.67, true, 'z', 0.876, {\"innerProp2\":false}, \"str2\", [45, false]]}";
//        StringDecoder decoder = new StringStringDecoder(testString);
//        NodeSerializedDataWrapper dataWrapper = new SkeletonNodeSerializedDataWrapper(decoder);
//
//        JsonNodeDeserializerHandler handler = new JsonNodeDeserializerHandler(dataWrapper, null, null);
//        NodeDeserializer deserializer = new SkeletonNodeDeserializer(handler);
//        deserializer.deserialize();
//
//        System.out.println(handler.getNode());
//    }
//}
