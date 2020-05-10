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

//< SKEL-31
public class ClassSignatureSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ClassSignatureSEH.class);

    private final AnnotationHandler annotationHandler;

    private String name;
    private int modifiers;

    public ClassSignatureSEH(AnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationHandler.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        if (maybeAnnotation.isPresent()){
            result = true;
            name = clazz.getTypeName();
            modifiers = clazz.getModifiers();
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureItem.CLASS));
        collector.beginObject(name);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return false;
    }
}
