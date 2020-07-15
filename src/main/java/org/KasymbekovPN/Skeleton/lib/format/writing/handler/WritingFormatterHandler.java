package org.KasymbekovPN.Skeleton.lib.format.writing.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;

public interface WritingFormatterHandler {
//    void handle(Node node);
    //<
    void addBeginBorder();
    void addEndBorder();
    void addValue(Node node);
}
