package org.KasymbekovPN.Skeleton.serialization.handler.method;

import org.KasymbekovPN.Skeleton.annotation.SkeletonArguments;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMethod;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMethodToString;
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

public class ToStringMethodSEH extends BaseSEH {

    private static final Logger log  = LoggerFactory.getLogger(ToStringMethodSEH.class);

    private final AnnotationHandler annotationHandler;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private final Map<String, MethodSignature> methods = new HashMap<>();
    private final Set<String> validKeys = new HashSet<>();

    public ToStringMethodSEH(AnnotationHandler annotationHandler, CollectorCheckingHandler collectorCheckingHandler) {
        this.annotationHandler = annotationHandler;
        this.collectorCheckingHandler = collectorCheckingHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {

        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationHandler.check(
                clazz.getDeclaredAnnotations(),
                SkeletonMethodToString.class,
                SkeletonMethod.class);

        if (maybeAnnotation.isPresent()){

            methods.clear();
            validKeys.clear();

            SkeletonMethodToString annotation = (SkeletonMethodToString) maybeAnnotation.get();
            SkeletonArguments[] arguments = annotation.arguments();
            String name = annotation.name();

            for (SkeletonArguments argumentsItem : arguments) {
                String uuid = UUID.randomUUID().toString();
                Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.add(uuid);
                List<String> args = Arrays.asList(argumentsItem.arguments());
                methods.put(uuid, new MethodSignature(name, args));
                maybeProcess.ifPresent(collectorCheckingProcess -> new MembersExistCheckingHandler(
                        collectorCheckingProcess,
                        ObjectNode.class,
                        args,
                        collector.getCollectorStructure().getPath(CollectorStructureItem.MEMBERS)
                ));
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
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureItem.METHOD));
        for (Map.Entry<String, MethodSignature> entry : methods.entrySet()) {
            String key = entry.getKey();
            if (validKeys.contains(key)){
                collector.beginObject(key);
                collector.addProperty("name", entry.getValue().getName());
                collector.beginArray("arguments");
                for (String argument : entry.getValue().getArguments()) {
                    collector.addProperty(argument);
                }
                collector.end();
                collector.end();
            }
        }
        collector.reset();

        return false;
    }

    private static class MethodSignature{

        private final String name;
        private final List<String> arguments;

        public String getName() {
            return name;
        }

        public List<String> getArguments() {
            return arguments;
        }

        public MethodSignature(String name, List<String> arguments) {
            this.name = name;
            this.arguments = arguments;
        }
    }
}
