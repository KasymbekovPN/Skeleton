package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServiceSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ServiceSEH.class);
    private static final List<String> SERVICE_PART_PATH = new ArrayList<>(){{add("__service");}};
    private static final String PATHS_PART_NAME = "paths";

    private final AnnotationChecker annotationChecker;

    private Map<EntityItem, List<String>> collectorStructure;

    public ServiceSEH(AnnotationChecker annotationChecker) {
        this.annotationChecker = annotationChecker;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationChecker.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        if (maybeAnnotation.isPresent()){
            SkeletonClass annotation = (SkeletonClass) maybeAnnotation.get();
            result = true;
            collectorStructure = collector.getCollectorStructure().getPaths();
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(SERVICE_PART_PATH);
        collector.beginObject(PATHS_PART_NAME);
        for (Map.Entry<EntityItem, List<String>> entry : collectorStructure.entrySet()) {
            String part = entry.getKey().toString();
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
