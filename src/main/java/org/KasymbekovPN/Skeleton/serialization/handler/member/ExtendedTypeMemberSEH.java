package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.ClassCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExtendedTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ExtendedTypeMemberSEH.class);
    private static final Class<? extends Annotation> ANNOTATION = SkeletonClass.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    private final Class<?> extendableType;

    public ExtendedTypeMemberSEH(Class<?> extendableType) {
        this.extendableType = extendableType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator, ClassCondition condition) {
        String name = field.getName();
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();

        if (extendableType.isAssignableFrom(type) &&
                (field.isAnnotationPresent(ANNOTATION) || condition.check(name, modifiers))){

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("type", type.getCanonicalName());
            generator.addProperty("modifiers", modifiers);
            generator.reset();

            return true;
        }

        return false;
    }
}
