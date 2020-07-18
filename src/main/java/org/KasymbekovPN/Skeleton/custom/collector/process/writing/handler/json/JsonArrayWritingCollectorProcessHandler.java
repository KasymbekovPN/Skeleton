package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.WritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

import java.util.List;

public class JsonArrayWritingCollectorProcessHandler implements WritingCollectorProcessHandler {

    @Override
    public void handle(Node node, WritingFormatter writingFormatter, CollectorProcess collectorProcess) {
        List<Node> children = ((ArrayNode) node).getChildren();

        writingFormatter.addBeginBorder(node);
        for (Node child : children) {
            child.apply(collectorProcess);
        }
        writingFormatter.addEndBorder(node);
    }
}