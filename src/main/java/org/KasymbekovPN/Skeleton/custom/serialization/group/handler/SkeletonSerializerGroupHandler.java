package org.KasymbekovPN.Skeleton.custom.serialization.group.handler;

import org.KasymbekovPN.Skeleton.custom.serialization.group.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.serialization.group.SerializerGroup;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupHandler;

import java.util.Map;

//< need test
public class SkeletonSerializerGroupHandler implements SerializerGroupHandler {

    @Override
    public void visit(SerializerGroup skeletonSerializerGroup) {
        Map<Class<?>, Node> prepareClasses = ((SkeletonSerializerGroup) skeletonSerializerGroup).getPrepareClasses();
    }

    //<
//    @Override
//    public void accept(Map<Class<?>, Node> prepareClasses) {
//
//    }
    //<
//    @Override
//    public void accept(EntityItem serializer, Collector collector) {
//
//    }
}
