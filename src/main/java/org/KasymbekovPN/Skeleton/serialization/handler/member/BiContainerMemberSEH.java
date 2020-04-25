package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.condition.Condition;
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

public class BiContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(BiContainerMemberSEH.class);
    private static final Integer ARGUMENTS_NUMBER = 2;

    private final Class<?> specificType;
    private final Checker<Class<?>> firstArgumentChecker;
    private final Checker<Class<?>> secondArgumentChecker;


    public BiContainerMemberSEH(Class<?> specificType,
                                Checker<Class<?>> firstArgumentChecker,
                                Checker<Class<?>> secondArgumentChecker) {
        this.firstArgumentChecker = firstArgumentChecker;
        this.secondArgumentChecker = secondArgumentChecker;
        this.specificType = specificType;
    }

    public BiContainerMemberSEH(Class<?> specificType, Checker<Class<?>> argumentChecker) {
        this.specificType = specificType;
        this.firstArgumentChecker = argumentChecker;
        this.secondArgumentChecker = argumentChecker;
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
            if (resumeCheckResults(results).equals(INCLUDE)){
                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                if (ARGUMENTS_NUMBER.equals(actualTypeArguments.length)){
                    Class<?> firstArg = (Class<?>) actualTypeArguments[0];
                    Class<?> secondArg = (Class<?>) actualTypeArguments[1];

                    if (firstArgumentChecker.check(firstArg) && secondArgumentChecker.check(secondArg)){
                        List<String> path = condition.getMemberPath(field.getAnnotations());
                        if (path.size() > 0){
                            generator.setTarget(path);
                            generator.beginObject(name);
                            generator.addProperty("type", type.getCanonicalName());
                            generator.addProperty("modifiers", modifiers);
                            generator.beginArray("arguments");
                            generator.addProperty(firstArg.getCanonicalName());
                            generator.addProperty(secondArg.getCanonicalName());
                            generator.reset();

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
