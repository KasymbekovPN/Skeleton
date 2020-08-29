package org.KasymbekovPN.Skeleton.lib.collector;

import org.KasymbekovPN.Skeleton.lib.node.Node;

import java.util.List;

public interface Collector {
    void clear();
    void reset();

    Node beginObject(String property);
    Node beginObject();

    Node addProperty(String property, String value);
    Node addProperty(String property, Number value);
    Node addProperty(String property, Boolean value);
    Node addProperty(String property, Character value);

    Node beginArray(String property);
    Node beginArray();

    Node addProperty(String value);
    Node addProperty(Number value);
    Node addProperty(Boolean value);
    Node addProperty(Character value);

    Node end();

    Node setTarget(List<String> path);

    Node getNode();
    Node detachNode();
    Node attachNode(Node node);
}
