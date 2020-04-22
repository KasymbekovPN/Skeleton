package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.KasymbekovPN.Skeleton.utils.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * SEH - Serialization Element Handler
 */
public class BiContainerMemberSEH implements SerializationElementHandler {

    //<

//    public class SimpleContainerMemberSEH implements SerializationElementHandler {
//

    private static final Logger log = LoggerFactory.getLogger(BiContainerMemberSEH.class);
    private static final Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};
    private static final Integer ARGUMENTS_NUMBER = 2;

    private final Class<?> specificType;
    private final Checker<Class<?>> firstArgumentChecker;
    private final Checker<Class<?>> secondArgumentChecker;


    public BiContainerMemberSEH(Class<?> specificType, Checker<Class<?>> firstArgumentChecker, Checker<Class<?>> secondArgumentChecker) {
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
    public boolean handle(SerializationElement serializationElement, Generator generator) {
        Field field = ((MemberSE) serializationElement).getData();

        if (field.getType().equals(specificType) && field.isAnnotationPresent(ANNOTATION)) {
            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            if (ARGUMENTS_NUMBER.equals(actualTypeArguments.length)){
                Class<?> firstArg = (Class<?>) actualTypeArguments[0];
                Class<?> secondArg = (Class<?>) actualTypeArguments[1];

                if (firstArgumentChecker.check(firstArg) && secondArgumentChecker.check(secondArg)){
                    String name = field.getName();
                    int modifiers = field.getModifiers();

                    generator.setTarget(PATH);
                    generator.beginObject(name);
                    generator.addProperty("type", field.getType().getCanonicalName());
                    generator.addProperty("modifiers", modifiers);
                    generator.beginArray("arguments");
                    generator.addProperty(firstArg.getCanonicalName());
                    generator.addProperty(secondArg.getCanonicalName());
                    generator.reset();

                    return true;
                }
            }
        }

        return false;
    }
}
