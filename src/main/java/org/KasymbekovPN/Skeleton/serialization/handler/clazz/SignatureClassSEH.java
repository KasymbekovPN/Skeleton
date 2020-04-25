package org.KasymbekovPN.Skeleton.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.condition.Condition;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

public class SignatureClassSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SignatureClassSEH.class);

    @Override
    protected boolean runHandlingImplementation(Class<?> clazz, Generator generator, Condition condition) {

        String name = clazz.getCanonicalName();
        int modifiers = clazz.getModifiers();
        Annotation[] annotations = clazz.getDeclaredAnnotations();

        MemberCheckResult result = condition.checkClass(annotations);
        if (result.equals(INCLUDE)){
            List<String> path = condition.getClassPath();

            generator.setTarget(path);
            generator.beginObject(name);
            generator.addProperty("modifiers", modifiers);
            generator.reset();
        }

        return false;
    }
}
