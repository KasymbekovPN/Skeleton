package org.KasymbekovPN.Skeleton.custom.deserialization.node.deserializer;

import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//< !!! del
public class SkeletonNodeDeserializer implements NodeDeserializer {

    static private final Logger log = LoggerFactory.getLogger(SkeletonNodeDeserializer.class);

    private NodeDeserializerHandler handler;

    public SkeletonNodeDeserializer(NodeDeserializerHandler handler) {
        this.handler = handler;
    }

    @Override
    public void deserialize() {
        do {
            handler = handler.run();
        } while (handler != null);
    }
}
