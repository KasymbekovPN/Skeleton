package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ClassSignatureSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ClassSignatureSEH.class);

    private final AnnotationChecker annotationChecker;

    private String name;
    private int modifiers;

    public ClassSignatureSEH(AnnotationChecker annotationChecker) {
        this.annotationChecker = annotationChecker;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationChecker.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        if (maybeAnnotation.isPresent()){
            SkeletonClass annotation = (SkeletonClass) maybeAnnotation.get();
            result = true;
            name = annotation.name();
            modifiers = clazz.getModifiers();
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.classEI()));
        collector.beginObject(name);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return false;
    }
}
