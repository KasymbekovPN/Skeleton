package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.containerArgumentChecker.ContainerArgumentChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class SimpleContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SimpleContainerMemberSEH.class);

    private final Class<?> specificType;
    private final ContainerArgumentChecker containerArgumentChecker;

    public SimpleContainerMemberSEH(Class<?> specificType, ContainerArgumentChecker containerArgumentChecker) {
        this.specificType = specificType;
        this.containerArgumentChecker = containerArgumentChecker;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Collector collector,
                                                AnnotationHandler annotationHandler) {

        final Class<?> type = field.getType();
        if (type.equals(specificType)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            Optional<List<Class<?>>> maybeArguments = containerArgumentChecker.check(actualTypeArguments);

            annotationHandler.check(name);
            annotationHandler.check(modifiers);
            annotationHandler.check(annotations);

            if (annotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE) &&
                maybeArguments.isPresent()){

                    annotationHandler.getContainer().addMemberName(name);

                    collector.setTarget(annotationHandler.getPath());
                    collector.beginObject(name);
                    collector.addProperty("type", type.getCanonicalName());
                    collector.addProperty("modifiers", modifiers);
                    collector.beginArray("arguments");

                    for (Class<?> argumentType : maybeArguments.get()) {
                        collector.addProperty(argumentType.getCanonicalName());
                    }

                    collector.reset();

                    return true;
            }
        }

        return false;
    }
}
