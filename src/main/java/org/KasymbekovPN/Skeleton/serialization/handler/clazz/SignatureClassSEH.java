package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class SignatureClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SignatureClassSEH.class);

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler) {

        String name = clazz.getCanonicalName();
        int modifiers = clazz.getModifiers();
        Annotation[] annotations = clazz.getDeclaredAnnotations();

        MemberCheckResult result = annotationConditionHandler.check(annotations);
        if (result.equals(INCLUDE)){
            Optional<List<String>> maybePath = annotationConditionHandler.getParentPath(annotations);
            maybePath.ifPresent(value -> {
                generator.setTarget(value);
                generator.beginObject(name);
                generator.addProperty("modifiers", modifiers);
                generator.reset();
            });
        }

        return false;
    }
}
