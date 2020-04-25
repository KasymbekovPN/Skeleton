package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.ClassCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class SignatureClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SignatureClassSEH.class);
    private static Class<? extends Annotation> ANNOTATION = SkeletonClass.class;
    private static final List<String> PATH = new ArrayList<>(){{add("class");}};

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Generator generator, ClassCondition condition) {
        if (clazz.isAnnotationPresent(ANNOTATION)){
            condition.setData(clazz.getAnnotation(ANNOTATION));

            String name = clazz.getCanonicalName();
            int modifiers = clazz.getModifiers();

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("modifiers", modifiers);
            generator.reset();
        }
        return false;
    }
}
