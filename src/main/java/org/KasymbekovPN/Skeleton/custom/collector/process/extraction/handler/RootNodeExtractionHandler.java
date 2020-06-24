package org.KasymbekovPN.Skeleton.custom.collector.process.extraction.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;

public class RootNodeExtractionHandler implements CollectorProcessHandler {

    private Node root;

    public RootNodeExtractionHandler(Node root, CollectorProcess collectorProcess, Class<? extends Node> clazz) {
        this.root = root;
        collectorProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {
        if (node.isObject()) {
            //< deepcopy to root
        }
    }
}
