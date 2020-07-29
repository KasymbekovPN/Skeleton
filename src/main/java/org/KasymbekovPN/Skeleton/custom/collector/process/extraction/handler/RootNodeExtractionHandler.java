package org.KasymbekovPN.Skeleton.custom.collector.process.extraction.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootNodeExtractionHandler implements CollectorProcessHandler {

    static private final Logger log = LoggerFactory.getLogger(RootNodeExtractionHandler.class);

    private Node root;

    public RootNodeExtractionHandler(Node root, CollectorProcess collectorProcess, EntityItem nodeEi) {
        this.root = root;
        collectorProcess.addHandler(nodeEi, this);
    }

    @Override
    public CollectorCheckingResult handle(Node node) {
        if (node.isObject()) {
            root.deepSet(node.deepCopy(root.getParent()));
        }

        return CollectorCheckingResult.NONE;
    }
    //<
//    @Override
//    public void handle(Node node) {
//        if (node.isObject()) {
//            root.deepSet(node.deepCopy(root.getParent()));
//        }
//    }
}
