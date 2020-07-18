package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

public interface WritingCollectorProcessHandler {
    void handle(Node node, WritingFormatter writingFormatter, CollectorProcess collectorProcess);
}
