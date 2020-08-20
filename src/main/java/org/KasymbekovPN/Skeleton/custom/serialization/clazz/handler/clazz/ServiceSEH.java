package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.annotation.Annotation;
import java.util.*;

public class ServiceSEH extends BaseSEH {

    private final Filter<Annotation> annotationFilter;
    private final List<String> servicePaths;
    private final Map<String, List<String>> paths;

    public ServiceSEH(Filter<Annotation> annotationFilter,
                      List<String> servicePaths,
                      Map<String, List<String>> paths) {
        this.annotationFilter = annotationFilter;
        this.servicePaths = servicePaths;
        this.paths = paths;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        Deque<Annotation> filteredAnnotation
                = annotationFilter.filter(new ArrayDeque<Annotation>(Arrays.asList(clazz.getDeclaredAnnotations())));

        return filteredAnnotation.size() > 0;
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
