package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ExtendedTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ExtendedTypeMemberSEH.class);

    private final Class<?> extendableType;

    public ExtendedTypeMemberSEH(Class<?> extendableType) {
        this.extendableType = extendableType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler) {

        Class<?> type = field.getType();
        if (extendableType.isAssignableFrom(type)) {
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            HashSet<MemberCheckResult> results = new HashSet<>() {{
                add(annotationConditionHandler.check(name));
                add(annotationConditionHandler.check(modifiers));
                add(annotationConditionHandler.check(annotations));
            }};
            if (resumeCheckResults(results).equals(INCLUDE)){
                Optional<List<String>> maybePath = annotationConditionHandler.getParentPath(annotations);
                if (maybePath.isPresent()){
                    generator.setTarget(maybePath.get());
                    generator.beginObject(name);
                    generator.addProperty("type", type.getCanonicalName());
                    generator.addProperty("modifiers", modifiers);
                    generator.reset();

                    return true;
                }
            }
        }

        return false;
    }
}
