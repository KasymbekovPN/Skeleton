package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.condition.Condition;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

public class SpecificTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SpecificTypeMemberSEH.class);

    private final Class<?> specificType;

    public SpecificTypeMemberSEH(Class<?> specificType) {
        this.specificType = specificType;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Generator generator, Condition condition) {

        Class<?> type = field.getType();
        if (type.equals(specificType)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            HashSet<MemberCheckResult> results = new HashSet<>() {{
                add(condition.checkMember(name));
                add(condition.checkMember(modifiers));
                add(condition.checkMember(annotations));
            }};
            if(resumeCheckResults(results).equals(INCLUDE)){
                List<String> path = condition.getMemberPath(annotations);
                if (path.size() > 0){
                    generator.setTarget(path);
                    generator.beginObject(name);
                    generator.addProperty("type", specificType.getCanonicalName());
                    generator.addProperty("modifiers", modifiers);
                    generator.reset();

                    return true;
                }
            }
        }

        return false;
    }
}
