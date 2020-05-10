package org.KasymbekovPN.Skeleton.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.annotation.SkeletonArguments;
import org.KasymbekovPN.Skeleton.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking.MembersExistCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.format.collector.CollectorStructureItem;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

//< SKEL-31
public class ConstructorClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ConstructorClassSEH.class);

    private final AnnotationHandler annotationHandler;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private final Map<String, List<String>> constructorArguments = new HashMap<>();
    private final Set<String> validKeys = new HashSet<>();

    public ConstructorClassSEH(AnnotationHandler annotationHandler,
                               CollectorCheckingHandler collectorCheckingHandler) {
        this.annotationHandler = annotationHandler;
        this.collectorCheckingHandler = collectorCheckingHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {

        boolean result = false;

        Optional<Annotation> maybeAnnotation = annotationHandler.check(clazz.getDeclaredAnnotations(), SkeletonConstructor.class);
        if (maybeAnnotation.isPresent()){

            constructorArguments.clear();
            SkeletonArguments[] arguments = ((SkeletonConstructor) maybeAnnotation.get()).arguments();

            for (SkeletonArguments argumentsItem : arguments) {
                String uuid = UUID.randomUUID().toString();
                Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.add(uuid);
                List<String> args = Arrays.asList(argumentsItem.arguments());
                constructorArguments.put(uuid, args);
                maybeProcess.ifPresent(collectorCheckingProcess -> new MembersExistCheckingHandler(
                        collectorCheckingProcess,
                        ObjectNode.class,
                        args,
                        collector.getCollectorStructure().getPath(CollectorStructureItem.MEMBERS)));
            }

            Map<String, SkeletonCheckResult> results = collectorCheckingHandler.handle(collector, true);
            for (Map.Entry<String, SkeletonCheckResult> entry : results.entrySet()) {
                if (entry.getValue().equals(SkeletonCheckResult.INCLUDE)){
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
