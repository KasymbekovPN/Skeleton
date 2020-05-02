package org.KasymbekovPN.Skeleton.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class ConstructorClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ConstructorClassSEH.class);

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector, AnnotationHandler annotationHandler) {

        Annotation[] annotations = clazz.getDeclaredAnnotations();
        annotationHandler.check(annotations);
        SkeletonCheckResult result = annotationHandler.getCheckResult();
        if (result.equals(SkeletonCheckResult.INCLUDE)){
            SkeletonConstructor annotation = (SkeletonConstructor) annotationHandler.getAnnotation();
            Set<String> memberName = annotationHandler.getContainer().getMemberName();
            String[] members = annotation.members();
            List<String> path = annotationHandler.getPath();

            collector.setTarget(path);
            collector.beginArray("ctrIndex");

            for (String member : members) {
                if (memberName.contains(member)){
                    collector.addProperty(member);
                }
            }

            collector.reset();
        }

        return false;
    }
}
