package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

//< SKEL-31
public class ClassExistCheckingHandler implements CollectorHandlingProcessHandler {

    private static final Logger log = LoggerFactory.getLogger(ClassExistCheckingHandler.class);

    private final CollectorCheckingProcess collectorCheckingProcess;
    private final Class<? extends Node> clazz;
    private final List<String> path;

    public ClassExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                     Class<? extends Node> clazz,
                                     List<String> path) {
        this.clazz = clazz;
        this.path = path;
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {

        SkeletonCheckResult result = SkeletonCheckResult.EXCLUDE;
        if (node.isObject()){
            Optional<Node> maybeChild = ((ObjectNode) node).getChild(path, ObjectNode.class);
            if (maybeChild.isPresent()){
                result = SkeletonCheckResult.INCLUDE;
            }
        }

        collectorCheckingProcess.setResult(clazz, result);
    }
}
