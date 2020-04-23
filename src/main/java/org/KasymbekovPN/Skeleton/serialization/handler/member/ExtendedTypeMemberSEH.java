package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.KasymbekovPN.Skeleton.utils.GeneralCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SEH - Serialization Element Handler
 */
public class ExtendedTypeMemberSEH implements SerializationElementHandler {

    private static final Logger log = LoggerFactory.getLogger(ExtendedTypeMemberSEH.class);
    private static final Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    private final Class<?> extendableType;

    public ExtendedTypeMemberSEH(Class<?> extendableType) {
        this.extendableType = extendableType;
    }

    @Override
    public boolean handle(SerializationElement serializationElement, Generator generator, GeneralCondition generalCondition) {
        Field field = ((MemberSE) serializationElement).getData();

        String name = field.getName();
        int modifiers = field.getModifiers();
        Class<?> type = field.getType();

        if (extendableType.isAssignableFrom(type) &&
                (field.isAnnotationPresent(ANNOTATION) || generalCondition.check(name, modifiers))){

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
