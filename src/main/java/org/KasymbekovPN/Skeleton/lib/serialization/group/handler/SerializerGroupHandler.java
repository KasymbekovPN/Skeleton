package org.KasymbekovPN.Skeleton.lib.serialization.group.handler;

import org.KasymbekovPN.Skeleton.lib.serialization.group.SerializerGroup;

//< ??? rename with ...Visitor
public interface SerializerGroupHandler {
//    void accept(EntityItem serializer, Collector collector);
    //<
    //< rename ???
//    void accept(Map<Class<?>, Node> prepareClasses);
    //<
    void visit(SerializerGroup skeletonSerializerGroup);
}
