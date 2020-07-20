package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ClassExistCheckingHandler implements CollectorProcessHandler {

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
        //<
//        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {

        CollectorCheckingResult result = CollectorCheckingResult.EXCLUDE;
        if (node.isObject()){
            Optional<Node> maybeChild = ((ObjectNode) node).getChild(path, ObjectNode.class);
            if (maybeChild.isPresent()){
                result = CollectorCheckingResult.INCLUDE;
            }
        }

        collectorCheckingProcess.setResult(clazz, result);
    }
}
