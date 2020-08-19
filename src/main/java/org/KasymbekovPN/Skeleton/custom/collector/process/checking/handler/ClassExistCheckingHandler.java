package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

//<
//import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
////< del
//public class ClassExistCheckingHandler implements CollectorProcessHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(ClassExistCheckingHandler.class);
//
//    private final CollectorCheckingProcess collectorCheckingProcess;
//    private final EntityItem nodeEi;
//    private final List<String> path;
//
//    public ClassExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
//                                     EntityItem nodeEi,
//                                     List<String> path) {
//        this.nodeEi = nodeEi;
//        this.path = path;
//        this.collectorCheckingProcess = collectorCheckingProcess;
//        this.collectorCheckingProcess.addHandler(nodeEi, this);
//    }
//
//    @Override
//    public CollectorCheckingResult handle(Node node) {
//
//        CollectorCheckingResult result = CollectorCheckingResult.EXCLUDE;
//        if (node.isObject()){
//            Optional<Node> maybeChild = node.getChild(path, ObjectNode.class);
//            if (maybeChild.isPresent()){
//                result = CollectorCheckingResult.INCLUDE;
//            }
//        }
//
//        collectorCheckingProcess.setResult(nodeEi, result);
//
//        return result;
//    }
//    //<
////    @Override
////    public void handle(Node node) {
////
////        CollectorCheckingResult result = CollectorCheckingResult.EXCLUDE;
////        if (node.isObject()){
////            Optional<Node> maybeChild = node.getChild(path, ObjectNode.class);
////            if (maybeChild.isPresent()){
////                result = CollectorCheckingResult.INCLUDE;
////            }
////        }
////
////        collectorCheckingProcess.setResult(nodeEi, result);
////    }
//}
