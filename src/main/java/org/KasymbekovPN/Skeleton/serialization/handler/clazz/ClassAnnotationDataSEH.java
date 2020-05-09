package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.format.collector.CollectorStructureItem;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ClassAnnotationDataSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ClassAnnotationDataSEH.class);

    private final AnnotationHandler annotationHandler;

    private int excludeByModifiers;
    private int includeByModifiers;
    private String[] includeByName;
    private String[] excludeByName;

    public ClassAnnotationDataSEH(AnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationHandler.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        if (maybeAnnotation.isPresent()){
            result = true;

            SkeletonClass annotation = (SkeletonClass) maybeAnnotation.get();
            includeByName = annotation.includeByName();
            excludeByName = annotation.excludeByName();
            includeByModifiers = annotation.includeByModifiers();
            excludeByModifiers = annotation.excludeByModifiers();
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureItem.ANNOTATION));
        collector.addProperty("excludeByModifiers", excludeByModifiers);
        collector.addProperty("includeByModifiers", includeByModifiers);

        collector.beginArray("excludeByName");
        for (String name : excludeByName) {
            collector.addProperty(name);
        }
        collector.end();

        collector.beginArray("includeByName");
        for (String name : includeByName) {
            collector.addProperty(name);
        }
        collector.reset();

        return false;
    }
}
