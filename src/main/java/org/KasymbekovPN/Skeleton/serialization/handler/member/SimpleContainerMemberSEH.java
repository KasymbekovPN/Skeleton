package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SimpleContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SimpleContainerMemberSEH.class);

    private final Checker<Class<?>> checker;
    private final Class<?> specificType;

    public SimpleContainerMemberSEH(Class<?> specificType, Checker<Class<?>> checker) {
        this.checker = checker;
        this.specificType = specificType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler) {

        final Class<?> type = field.getType();
        if (type.equals(specificType)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            HashSet<MemberCheckResult> results = new HashSet<>() {{
                add(annotationConditionHandler.check(name));
                add(annotationConditionHandler.check(modifiers));
                add(annotationConditionHandler.check(annotations));
            }};
            if (resumeCheckResults(results).equals(INCLUDE)) {
                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                if (actualTypeArguments.length == 1)
                {
                    Class<?> argClass = (Class<?>) actualTypeArguments[0];
                    Optional<List<String>> maybePath = annotationConditionHandler.getParentPath(annotations);
                    if (checker.check(argClass) && maybePath.isPresent())
                    {
                        generator.setTarget(maybePath.get());
                        generator.beginObject(name);
                        generator.addProperty("type", type.getCanonicalName());
                        generator.addProperty("modifiers", modifiers);
                        generator.beginArray("arguments");
                        generator.addProperty(argClass.getCanonicalName());
                        generator.reset();

                        return true;
                    }
                }
            }
        }

        return false;
    }
}
