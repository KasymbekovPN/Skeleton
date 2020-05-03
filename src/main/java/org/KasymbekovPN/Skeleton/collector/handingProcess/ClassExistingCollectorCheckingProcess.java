package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ClassExistingCollectorCheckingProcess implements CollectorCheckingProcess {

    private static final Logger log = LoggerFactory.getLogger(ClassExistingCollectorCheckingProcess.class);

    private final Map<Class<? extends Node>, CollectorHandlingProcessHandler> handlers = new HashMap<>();

    private SkeletonCheckResult result = SkeletonCheckResult.NONE;

    @Override
    public void doIt(Node node) {
        Class<? extends Node> clazz = node.getClass();
        //<
        log.info("doIt : {}", clazz);
        log.info("doIt : {}", handlers);
        //<
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(node);
        } else {
            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
        }
    }

    @Override
    public void addHandler(Class<? extends Node> clazz, CollectorHandlingProcessHandler collectorHandlingProcessHandler) {
        //<
        log.info("addHandler : {}, {}", clazz, collectorHandlingProcessHandler);
        //<
        handlers.put(clazz, collectorHandlingProcessHandler);
        //<
        log.info("addHandler : {}", handlers);
        //<
    }

    @Override
    public void setResult(SkeletonCheckResult result) {
        //<
        log.info("result : {}", result);
                //<
        this.result = result;
    }

    @Override
    public SkeletonCheckResult getResult() {
        return result;
    }
}
