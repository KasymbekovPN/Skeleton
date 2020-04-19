package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * SEH - Serialization Element Handler
 */
public class SpecificTypeMemberSEH implements SerializationElementHandler {

    private static final Logger log = LoggerFactory.getLogger(SpecificTypeMemberSEH.class);
    private static final Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    private final Class<?> specificType;

    public SpecificTypeMemberSEH(Class<?> specificType) {
        this.specificType = specificType;
    }

    @Override
    public boolean handle(SerializationElement serializationElement, Generator generator) {
        Field field = ((MemberSE) serializationElement).getData();

        if (field.getType().equals(specificType) && field.isAnnotationPresent(ANNOTATION)){
            String name = field.getName();
            int modifiers = field.getModifiers();

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("type", specificType.getCanonicalName());
            generator.addProperty("modifiers", modifiers);
            generator.reset();

            return true;
        }
        return false;
    }
}
