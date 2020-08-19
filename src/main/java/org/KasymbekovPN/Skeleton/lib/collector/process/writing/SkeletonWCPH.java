package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

//< del
//import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
//
///**
// * WCPH - Writing Collector Process Handler
// */
//public class SkeletonWCPH implements CollectorProcessHandler {
//
//    private final WritingCollectorProcessHandler writingCollectorProcessHandler;
//    private final WritingFormatterHandler writingFormatterHandler;
//    private final CollectorProcess collectorProcess;
//    private final EntityItem ei;
//
//    public SkeletonWCPH(WritingCollectorProcessHandler writingCollectorProcessHandler,
//                        WritingFormatterHandler writingFormatterHandler,
//                        CollectorProcess collectorProcess,
//                        EntityItem ei) {
//        this.writingCollectorProcessHandler = writingCollectorProcessHandler;
//        this.writingFormatterHandler = writingFormatterHandler;
//        this.collectorProcess = collectorProcess;
//        this.ei = ei;
//
//        collectorProcess.addHandler(ei, this);
//    }
//
//    @Override
//    public CollectorCheckingResult handle(Node node) {
//        if (node.getEI().equals(ei)){
//            writingCollectorProcessHandler.handle(node, writingFormatterHandler, collectorProcess);
//        }
//
//        return CollectorCheckingResult.NONE;
//    }
//    //<
////    @Override
////    public void handle(Node node) {
////        if (node.getEI().equals(ei)){
////            writingCollectorProcessHandler.handle(node, writingFormatterHandler, collectorProcess);
////        }
////    }
//}