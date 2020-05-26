package org.KasymbekovPN.Skeleton.custom.serialization.handler.method;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonArguments;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMethod;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMethodToString;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.MembersExistCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public class ToStringMethodSEH extends BaseSEH {

    private static final Logger log  = LoggerFactory.getLogger(ToStringMethodSEH.class);

    private final AnnotationChecker annotationChecker;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private final Map<String, MethodSignature> methods = new HashMap<>();
    private final Set<String> validKeys = new HashSet<>();

    public ToStringMethodSEH(AnnotationChecker annotationChecker, CollectorCheckingHandler collectorCheckingHandler) {
        this.annotationChecker = annotationChecker;
        this.collectorCheckingHandler = collectorCheckingHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {

        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationChecker.check(
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
                List<String> args = Arrays.asList(argumentsItem.arguments());
                String hash = String.valueOf(args.hashCode());
                Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.add(hash);
                methods.put(hash, new MethodSignature(name, args));
                maybeProcess.ifPresent(collectorCheckingProcess -> new MembersExistCheckingHandler(
                        collectorCheckingProcess,
                        ObjectNode.class,
                        args,
                        collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI())
                ));
            }

            Map<String, CollectorCheckingResult> results = collectorCheckingHandler.handle(collector);
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
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.methodEI()));
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
