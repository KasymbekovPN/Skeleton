package org.KasymbekovPN.Skeleton.serialization.visitor.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.StringSMVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class StringSVEH implements SerializationVisitorElementHandler {

    private static final Logger log = LoggerFactory.getLogger(StringSVEH.class);
    private static final Class ANNOTATION = Skeleton.class;
    private static final Class TARGET_TYPE = String.class;
    private static final String TYPE = TARGET_TYPE.getCanonicalName();
    private static final String ENTITY = "member";

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {

        StringSMVE ve = (StringSMVE) serializationVE;
        Field field = ve.getData();

        if (field.getType().equals(TARGET_TYPE) && field.isAnnotationPresent(ANNOTATION)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            String key = String.valueOf((ENTITY + String.valueOf(modifiers) + TYPE + name).hashCode());

            generator.beginObject(key);
            generator.addProperty("entity", ENTITY);
            generator.addProperty("type", TYPE);
            generator.addProperty("modifiers", modifiers);
            generator.addProperty("name", name);
            generator.end();

            return true;
        }

        return false;
    }
}
