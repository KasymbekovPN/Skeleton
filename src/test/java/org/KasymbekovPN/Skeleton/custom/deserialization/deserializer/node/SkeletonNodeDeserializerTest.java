package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SkeletonNodeDeserializer. Testing of:")
public class SkeletonNodeDeserializerTest {

    @Test
    void test() throws Exception {

        String testString = "{\"property1\" : true, \"arrProperty\" : [true, false, true]}";
        StringDecoder decoder = new StringStringDecoder(testString);
        NodeSerializedDataWrapper dataWrapper = new SkeletonNodeSerializedDataWrapper(decoder);
        Collector collector = Utils.createCollector();

        SkeletonNodeDeserializer.InitialHandler handler
                = new SkeletonNodeDeserializer.InitialHandler(dataWrapper, null, null);

        NodeDeserializer deserializer = new SkeletonNodeDeserializer(handler);
        deserializer.deserialize();
    }
}
