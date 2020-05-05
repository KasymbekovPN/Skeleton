package org.KasymbekovPN.Skeleton.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking.MembersExistCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public class ConstructorClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ConstructorClassSEH.class);

    //< skel-30
    private static List<String> PATH = new ArrayList<>(){{add("constructor");}};
    private static String MEMBERS_PROCESS = "members";

    private final AnnotationHandler annotationHandler;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private List<String> members;

    public ConstructorClassSEH(AnnotationHandler annotationHandler,
                               CollectorCheckingHandler collectorCheckingHandler) {
        this.annotationHandler = annotationHandler;
        this.collectorCheckingHandler = collectorCheckingHandler;

        this.collectorCheckingHandler.add(MEMBERS_PROCESS);
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {

        boolean result = false;

        Optional<Annotation> maybeAnnotation = annotationHandler.check(clazz.getDeclaredAnnotations(), SkeletonConstructor.class);
        Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.get(MEMBERS_PROCESS);
        if (maybeAnnotation.isPresent() && maybeProcess.isPresent()){

            SkeletonConstructor annotation = (SkeletonConstructor) maybeAnnotation.get();
            members = Arrays.asList(annotation.members());

            CollectorCheckingProcess process = maybeProcess.get();
            new MembersExistCheckingHandler(process, ObjectNode.class, members);
            Map<String, SkeletonCheckResult> collectorCheckingResults = collectorCheckingHandler.doIt(collector, true);

            result = collectorCheckingResults.get(MEMBERS_PROCESS).equals(SkeletonCheckResult.INCLUDE);
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(PATH);
        collector.beginArray("arguments");
        for (String member : members) {
            collector.addProperty(member);
        }
        collector.reset();

        return false;
    }
}
