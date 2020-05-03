package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassExistCheckingHandler implements CollectorHandlingProcessHandler {

    private static final Logger log = LoggerFactory.getLogger(ClassExistCheckingHandler.class);

    //< SKEL-30
    private static final String CLASS_OBJECT_PROPERTY = "class";

//    private SkeletonCheckResult checkResult = SkeletonCheckResult.NONE;
    //<
//    private final Processor processor;
    //<
    private final CollectorCheckingProcess collectorCheckingProcess;

    public ClassExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess, Class<? extends Node> clazz) {
//        this.processor = processor;
        //<
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {
        log.info("{}", node);
        //<

        SkeletonCheckResult checkResult = ((ObjectNode) node).getChildren().containsKey(CLASS_OBJECT_PROPERTY)
                ? SkeletonCheckResult.INCLUDE
                : SkeletonCheckResult.EXCLUDE;
        collectorCheckingProcess.setResult(checkResult);
    }
}
