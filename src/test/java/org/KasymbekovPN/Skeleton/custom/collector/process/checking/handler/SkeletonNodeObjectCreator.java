package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.List;

public class SkeletonNodeObjectCreator {

    public static ObjectNode create(List<String> path, ObjectNode node)
    {
        ObjectNode buffer = node;
        for (String pathItem : path) {
            buffer.getChildren().put(pathItem, new ObjectNode(buffer));
            buffer = (ObjectNode) buffer.getChildren().get(pathItem);
        }
        return buffer;
    }
}
