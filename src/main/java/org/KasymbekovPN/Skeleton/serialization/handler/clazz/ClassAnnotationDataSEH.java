package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassAnnotationDataSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ClassAnnotationDataSEH.class);

    //< skel-30
    private static final List<String> PATH = new ArrayList<>(){{add("annotation");}};

    private final AnnotationHandler annotationHandler;

    public ClassAnnotationDataSEH(AnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector) {

        Optional<Annotation> maybeAnnotation = annotationHandler.check(clazz.getDeclaredAnnotations());
        if (maybeAnnotation.isPresent()){

            SkeletonClass annotation = (SkeletonClass) maybeAnnotation.get();
            collector.setTarget(PATH);
            collector.addProperty("excludeByModifiers", annotation.excludeByModifiers());
            collector.addProperty("includeByModifiers", annotation.includeByModifiers());

            collector.beginArray("excludeByName");
            for (String excludedName : annotation.excludeByName()) {
                collector.addProperty(excludedName);
            }
            collector.end();

            collector.beginArray("includeByName");
            for (String includedName : annotation.includeByName()) {
                collector.addProperty(includedName);
            }
            collector.reset();
        }

        return false;
    }
}
