package org.KasymbekovPN.Skeleton.lib.serialization.group.handler;

import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;

public interface SerializerGroupVisitor {
    void visit(SkeletonSerializerGroup skeletonSerializerGroup);
}
