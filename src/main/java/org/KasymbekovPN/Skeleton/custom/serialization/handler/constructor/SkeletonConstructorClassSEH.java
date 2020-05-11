package org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonArguments;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.SkeletonMembersExistCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructureItem;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public class SkeletonConstructorClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SkeletonConstructorClassSEH.class);

    private final AnnotationChecker annotationChecker;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private final Map<String, List<String>> constructorArguments = new HashMap<>();
    private final Set<String> validKeys = new HashSet<>();

    public SkeletonConstructorClassSEH(AnnotationChecker annotationChecker,
                                       CollectorCheckingHandler collectorCheckingHandler) {
        this.annotationChecker = annotationChecker;
        this.collectorCheckingHandler = collectorCheckingHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {

        boolean result = false;

        Optional<Annotation> maybeAnnotation = annotationChecker.check(clazz.getDeclaredAnnotations(), SkeletonConstructor.class);
        if (maybeAnnotation.isPresent()){

            constructorArguments.clear();
            SkeletonArguments[] arguments = ((SkeletonConstructor) maybeAnnotation.get()).arguments();

            for (SkeletonArguments argumentsItem : arguments) {
                String uuid = UUID.randomUUID().toString();
                Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.add(uuid);
                List<String> args = Arrays.asList(argumentsItem.arguments());
                constructorArguments.put(uuid, args);
                maybeProcess.ifPresent(collectorCheckingProcess -> new SkeletonMembersExistCheckingHandler(
                        collectorCheckingProcess,
                        SkeletonObjectNode.class,
                        args,
                        collector.getCollectorStructure().getPath(CollectorStructureItem.MEMBERS)));
            }

            Map<String, CollectorCheckingResult> results = collectorCheckingHandler.handle(collector, true);
            for (Map.Entry<String, CollectorCheckingResult> entry : results.entrySet()) {
                if (entry.getValue().equals(CollectorCheckingResult.INCLUDE)){
                    validKeys.add(entry.getKey());
                    result = true;
                }
            }
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureItem.CONSTRUCTOR));
        for (Map.Entry<String, List<String>> entry : constructorArguments.entrySet()) {
            String key = entry.getKey();
            if (validKeys.contains(key)){
                collector.beginArray(key);
                for (String arg : entry.getValue()) {
                    collector.addProperty(arg);
                }
                collector.end();
            }
        }
        collector.reset();

        return false;
    }
}
