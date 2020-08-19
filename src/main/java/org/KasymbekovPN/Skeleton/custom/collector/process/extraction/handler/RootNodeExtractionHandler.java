package org.KasymbekovPN.Skeleton.custom.collector.process.extraction.handler;

//< !!! restore
//import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
////< del ???
//public class RootNodeExtractionHandler implements CollectorProcessHandler {
//
//    static private final Logger log = LoggerFactory.getLogger(RootNodeExtractionHandler.class);
//
//    private Node root;
//
//    public RootNodeExtractionHandler(Node root, CollectorProcess collectorProcess, EntityItem nodeEi) {
//        this.root = root;
//        collectorProcess.addHandler(nodeEi, this);
//    }
//
//    @Override
//    public CollectorCheckingResult handle(Node node) {
//        if (node.isObject()) {
//            root.deepSet(node.deepCopy(root.getParent()));
//        }
//
//        return CollectorCheckingResult.NONE;
//    }
//}
