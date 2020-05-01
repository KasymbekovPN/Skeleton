package org.KasymbekovPN.Skeleton.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.condition.AnnotationHandler;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstructorClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ConstructorClassSEH.class);

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector, AnnotationHandler annotationHandler) {
        return super.runHandlingImplementation(clazz, collector, annotationHandler);
    }
}
