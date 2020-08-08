package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

public interface WritingCollectorProcessHandler {
    void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess);
}
