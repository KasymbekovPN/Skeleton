package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServiceSEH extends BaseSEH {

    private final AnnotationChecker annotationChecker;
    private final List<String> servicePaths;
    private final Map<String, List<String>> paths;

    public ServiceSEH(AnnotationChecker annotationChecker,
                      List<String> servicePaths,
                      Map<String, List<String>> paths) {
        this.annotationChecker = annotationChecker;
        this.servicePaths = servicePaths;
        this.paths = paths;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        Optional<Annotation> maybeAnnotation = annotationChecker.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        return maybeAnnotation.isPresent();
    }

    @Override
    protected boolean fillCollector(Collector collector) {

        collector.setTarget(servicePaths);
        for (Map.Entry<String, List<String>> entry : paths.entrySet()) {
            String part = entry.getKey();
            List<String> path = entry.getValue();
            collector.beginArray(part);
            for (String pathItem : path) {
                collector.addProperty(pathItem);
            }
            collector.end();
        }
        collector.reset();

        return false;
    }
}
