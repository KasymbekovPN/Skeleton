package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
