package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ExtendedTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ExtendedTypeMemberSEH.class);

    private final Class<?> extendableType;

    public ExtendedTypeMemberSEH(Class<?> extendableType) {
        this.extendableType = extendableType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Collector collector,
                                                AnnotationHandler annotationHandler) {

        Class<?> type = field.getType();
        if (extendableType.isAssignableFrom(type)) {
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            annotationHandler.check(name);
            annotationHandler.check(modifiers);
            annotationHandler.check(annotations);
            if (annotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){

                annotationHandler.getContainer().addMemberName(name);

                collector.setTarget(annotationHandler.getPath());
                collector.beginObject(name);
                collector.addProperty("type", type.getCanonicalName());
                collector.addProperty("modifiers", modifiers);
                collector.reset();

                return true;
            }

            //<
//            HashSet<SkeletonCheckResult> results = new HashSet<>() {{
//                add(annotationConditionHandler.check(name));
//                add(annotationConditionHandler.check(modifiers));
//                add(annotationConditionHandler.check(annotations));
//            }};
//            if (resumeCheckResults(results).equals(INCLUDE)){
//                Optional<List<String>> maybePath = annotationConditionHandler.getParentPath(annotations);
//                if (maybePath.isPresent()){
//                    collector.setTarget(maybePath.get());
//                    collector.beginObject(name);
//                    collector.addProperty("type", type.getCanonicalName());
//                    collector.addProperty("modifiers", modifiers);
//                    collector.reset();
//
//                    return true;
//                }
//            }
        }

        return false;
    }
}
