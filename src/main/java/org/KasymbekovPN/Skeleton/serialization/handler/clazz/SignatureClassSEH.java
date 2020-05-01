package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.condition.AnnotationHandler;
import org.KasymbekovPN.Skeleton.condition.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

public class SignatureClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SignatureClassSEH.class);

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector,
                                                AnnotationHandler annotationHandler) {

        String name = clazz.getCanonicalName();
        int modifiers = clazz.getModifiers();
        Annotation[] annotations = clazz.getDeclaredAnnotations();

        annotationHandler.check(annotations);
        if (annotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
            List<String> path = annotationHandler.getPath();

            collector.setTarget(path);
            collector.beginObject(name);
            collector.addProperty("modifiers", modifiers);
            collector.reset();
        }

        return false;
    }
}
