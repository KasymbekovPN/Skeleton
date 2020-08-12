package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

import java.util.Iterator;
import java.util.List;

/**
 * WCPH - Writing Collector Process Handler
 */
public class JsonArrayWCPH implements WritingCollectorProcessHandler {

    @Override
    public void handle(Node node, WritingFormatterHandler writingFormatterHandler, CollectorProcess collectorProcess) {
        List<Node> children = ((ArrayNode) node).getChildren();

        writingFormatterHandler.addBeginBorder(node);

        Iterator<String> delimiterIterator = writingFormatterHandler.getDelimiters(children.size(), node).iterator();

        for (Node child : children) {
            writingFormatterHandler.addDelimiter(delimiterIterator);
            child.apply(collectorProcess);
        }
        writingFormatterHandler.addEndBorder(node);
    }
}