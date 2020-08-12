package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

/**
 * WCPH - Writing Collector Process Handler
 */
public class JsonPrimitiveWCPH implements WritingCollectorProcessHandler {

    @Override
    public void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess) {
        writingFormatterHandler.addValue(node);
    }
}
