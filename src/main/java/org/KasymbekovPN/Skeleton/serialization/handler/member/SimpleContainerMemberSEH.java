package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.Checker;
import org.KasymbekovPN.Skeleton.utils.ClassCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimpleContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SimpleContainerMemberSEH.class);
    private static final Class<? extends Annotation> ANNOTATION = SkeletonClass.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    private final Checker<Class<?>> checker;
    private final Class<?> specificType;

    public SimpleContainerMemberSEH(Class<?> specificType, Checker<Class<?>> checker) {
        this.checker = checker;
        this.specificType = specificType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator, ClassCondition condition) {
        String name = field.getName();
        int modifiers = field.getModifiers();

        if (field.getType().equals(specificType) &&
                (field.isAnnotationPresent(ANNOTATION) || condition.check(name, modifiers))){

            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            if (actualTypeArguments.length == 1)
            {
                Class<?> argClass = (Class<?>) actualTypeArguments[0];


                if (checker.check(argClass) && condition.check(name, modifiers))
                {
                    generator.setTarget(PATH);
                    generator.beginObject(name);
                    generator.addProperty("type", field.getType().getCanonicalName());
                    generator.addProperty("modifiers", modifiers);
                    generator.beginArray("arguments");
                    generator.addProperty(argClass.getCanonicalName());
                    generator.reset();

                    return true;
                }
            }
        }

        return false;
    }
}
