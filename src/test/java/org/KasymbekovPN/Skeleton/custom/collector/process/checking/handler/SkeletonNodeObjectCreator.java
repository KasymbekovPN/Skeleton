package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;

import java.util.List;

public class SkeletonNodeObjectCreator {

    public static SkeletonObjectNode create(List<String> path, SkeletonObjectNode node)
    {
        SkeletonObjectNode buffer = node;
        for (String pathItem : path) {
            buffer.getChildren().put(pathItem, new SkeletonObjectNode(buffer));
            buffer = (SkeletonObjectNode) buffer.getChildren().get(pathItem);
        }
        return buffer;
    }
}
