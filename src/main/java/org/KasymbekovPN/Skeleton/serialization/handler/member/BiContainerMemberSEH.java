package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.containerArgumentChecker.ContainerArgumentChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class BiContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(BiContainerMemberSEH.class);

    private final Class<?> specificType;
    private final ContainerArgumentChecker containerArgumentChecker;

    public BiContainerMemberSEH(Class<?> specificType, ContainerArgumentChecker containerArgumentChecker) {
        this.specificType = specificType;
        this.containerArgumentChecker = containerArgumentChecker;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler) {

        Class<?> type = field.getType();
        if (type.equals(specificType)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            HashSet<MemberCheckResult> results = new HashSet<>() {{
                add(annotationConditionHandler.check(name));
                add(annotationConditionHandler.check(modifiers));
                add(annotationConditionHandler.check(annotations));
            }};
            MemberCheckResult result = resumeCheckResults(results);
            if (result.equals(INCLUDE)){
                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                Optional<List<Class<?>>> maybeArguments = containerArgumentChecker.check(actualTypeArguments);
                Optional<List<String>> maybePath = annotationConditionHandler.getParentPath(annotations);

                if (maybeArguments.isPresent() && maybePath.isPresent()){
                    generator.setTarget(maybePath.get());
                    generator.beginObject(name);
                    generator.addProperty("type", type.getCanonicalName());
                    generator.addProperty("modifiers", modifiers);
                    generator.beginArray("arguments");

                    for (Class<?> argumentType : maybeArguments.get()) {
                        generator.addProperty(argumentType.getCanonicalName());
                    }

                    generator.reset();

                    return true;
                }
            }
        }

        return false;
    }
}
